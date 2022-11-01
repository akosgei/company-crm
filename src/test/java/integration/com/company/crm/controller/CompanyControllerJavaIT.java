package com.company.crm.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.company.crm.entity.Company;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class CompanyControllerJavaIT {

    @Autowired
    TestRestTemplate restTemplate;

    @Autowired
    ObjectMapper mapper;

    @Test
    void testImportCompanies() throws IOException {

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
