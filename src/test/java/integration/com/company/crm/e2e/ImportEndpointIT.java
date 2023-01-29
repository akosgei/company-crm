package com.company.crm.e2e;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWireMock(port = 9292)
class ImportEndpointIT {

    @Autowired
    TestRestTemplate httpClient;


    @Autowired
    WireMockServer wireMockServer;


    @BeforeEach
    void init() {
        //https://dummyjson.com/todos
    }


    @DynamicPropertySource
    static void overridehttpClientBaseUrl(DynamicPropertyRegistry propertyRegistry) {
        // propertyRegistry.add();
    }


    @Test
    void testWireMock() {
        assertThat(wireMockServer.isRunning()).isTrue();
        log.info("Base uri: {}", wireMockServer.baseUrl());
    }

    @Test
    void basicWireMockExample() {
        wireMockServer.stubFor(
                WireMock.get(urlEqualTo("/todos"))
                        .willReturn(aResponse()
                                .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                                .withBody("[]"))
        );

        ResponseEntity<String> response = this.httpClient.getForEntity(URI.create("" + wireMockServer.baseUrl() + "/todos"), String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo("[]");

    }
/*
    class PropertiesInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public PropertiesInitializer() {
        }

        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {
            TestPropertyValues.of(
                    "https://dummyjson.com/=" + "http://localhost:" + wireMockServer.port()
            ).applyTo(applicationContext.getEnvironment());
        }
    }*/
}
