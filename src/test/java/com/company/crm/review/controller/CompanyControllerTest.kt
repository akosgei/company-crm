package com.company.crm.review.controller

import io.restassured.RestAssured
import io.restassured.RestAssured.given
import io.restassured.response.Response
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner


@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class CompanyControllerTest {

    @Before
    fun setBaseUrl() {
        RestAssured.baseURI = "http://localhost:8080/company/"
    }

    @Test
    fun `should import companies`() {
        val response = importCompanies()
        Assert.assertEquals(200, response!!.statusCode())
    }

    @Test
    fun `imported company should display summary by id`() {
       importCompanies()
        val response = given()
                .get("summary/1")
                .then()
                .extract().response();

        Assert.assertEquals(200, response.statusCode());
        Assert.assertEquals("Dr. Enola Ortiz", response.jsonPath().getString("companyName"));
        Assert.assertEquals("3", response.jsonPath().getString("conversationCount"));
        Assert.assertEquals("3284,3372", response.jsonPath().getString("mostPopularUser"));
    }

    @Test
    fun `summary for non existing company should return an error`() {
        val response = given()
                .get("summary/100") //company with id 100 does not exist in test payload
                .then()
                .extract().response();

        Assert.assertEquals(404, response.statusCode());
        Assert.assertEquals("Company with supplied id not found!", response.jsonPath().getString("errorMessage"))
     }

    private fun jsonFile(name: String) = this::class.java.getResource("/$name").readText()

    private fun importCompanies(): Response? {
        val httpRequest = given()
        httpRequest.header("Content-Type", "application/json")
        httpRequest.body(jsonFile("companies.json"))
        return httpRequest.post("/import");
    }
}