package com.company.crm.smoketests;

import com.company.crm.configuration.CrmTestConfiguration;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import java.net.URI;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {CrmTestConfiguration.class})
@AutoConfigureWireMock(port = 9292, httpsPort = 9393)
class ImportEndpointIT {

    @Autowired
    TestRestTemplate httpClient;

    @Autowired
    WireMockServer wireMockServer;

    @DynamicPropertySource
    static void overrideProperties(DynamicPropertyRegistry propertyRegistry) {
        propertyRegistry.add("some defined property", () -> "some desired value");
    }


    @Test
    void testWireMock() {
        assertThat(wireMockServer.isRunning()).isTrue();
        log.info("Base uri: {}", wireMockServer.baseUrl());
    }

    @Test
    void basicWireMockExample() throws JSONException {
        wireMockServer.stubFor(
                WireMock.get(urlEqualTo("/todos"))
                        .willReturn(aResponse()
                                .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                                .withBodyFile("import-endpoint/todo.json"))
        );

        ResponseEntity<String> response = this.httpClient.getForEntity(URI.create("" + wireMockServer.baseUrl() + "/todos"), String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        JSONAssert.assertEquals("{\"todos\":[{\"id\":1,\"todo\":\"Do something nice for someone I care about\",\"completed\":true,\"userId\":26},{\"id\":2,\"todo\":\"Memorize the fifty states and their capitals\",\"completed\":false,\"userId\":48}],\"total\":2,\"skip\":0,\"limit\":30}", response.getBody(), false);
    }
}
