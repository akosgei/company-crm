package com.company.crm.controller;

import com.company.crm.entity.Company;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestPropertySource(locations = "classpath:application-test.yml")
class CompanyControllerJavaIT {

    @Autowired
    TestRestTemplate restTemplate;

    @Autowired
    ObjectMapper mapper;

    @Test
    void testImportCompanies() throws IOException {
        log.info("Running company controller import IT");
        //given some stuff
        File file = ResourceUtils.getFile("classpath:companies.json");
        List<Company> listOfCompanies = mapper.readValue(file, new TypeReference<>() {
        });

        //when doing some stuff
        HttpHeaders headers = new HttpHeaders();
        headers.set("content-type", MediaType.APPLICATION_JSON.toString());
        HttpEntity<List<Company>> requestPayload = new HttpEntity<>(listOfCompanies, headers);
        ResponseEntity<List<Company>> response = restTemplate.exchange("/company/import", HttpMethod.POST, requestPayload, new ParameterizedTypeReference<>() {
        });

        //then assert results
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
