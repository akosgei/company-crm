package com.company.crm.controller

import io.restassured.RestAssured
import io.restassured.RestAssured.given
import io.restassured.http.ContentType.JSON
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class CompanyControllerIT {

    @BeforeEach
    fun setBaseUrl() {
        RestAssured.baseURI = "http://localhost:8080"
    }

    @Test
    fun `should import companies`() {
        given()
            .body(jsonFile("companies.json"))
            .contentType(JSON)
            .post("/company/import")
            .then()
            .statusCode(200)
    }

    private fun jsonFile(name: String) = this::class.java.getResource("/$name").readText()
}