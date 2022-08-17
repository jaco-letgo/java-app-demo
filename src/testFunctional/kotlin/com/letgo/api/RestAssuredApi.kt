package com.letgo.api

import io.cucumber.datatable.DataTable
import io.cucumber.java.After
import io.cucumber.java.Before
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import io.restassured.RestAssured
import io.restassured.builder.RequestSpecBuilder
import io.restassured.config.LogConfig
import io.restassured.config.RestAssuredConfig
import io.restassured.filter.log.LogDetail
import io.restassured.http.ContentType
import io.restassured.module.kotlin.extensions.Extract
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.When
import io.restassured.response.Response
import io.restassured.specification.RequestSpecification
import org.junit.jupiter.api.Assertions.assertEquals
import org.springframework.boot.test.web.server.LocalServerPort

class RestAssuredApi {
    @LocalServerPort
    private val port = 0
    lateinit var requestSpecification: RequestSpecification
    private var requestEndpoint: String = ""
    private var requestHeaders: Map<String, String> = emptyMap()
    private var requestQueryParams: Map<String, String> = emptyMap()
    private var requestBody: String = ""
    private var responseCode: Int? = null
        get() = field ?: throw RuntimeException("no response from http call")
    private var responseBody: String? = null
        get() = field ?: throw RuntimeException("no response from http call")

    @Before
    fun setup() {
        requestSpecification = RequestSpecBuilder()
            .setBaseUri("http://localhost:$port")
            .setContentType(ContentType.JSON)
            .setRelaxedHTTPSValidation()
            .setConfig(
                RestAssuredConfig.config().logConfig(
                    LogConfig.logConfig().enableLoggingOfRequestAndResponseIfValidationFails(LogDetail.ALL)
                )
            )
            .build()
    }

    @After
    fun tearDown() {
        RestAssured.reset()
    }

    private fun get(): Response =
        Given {
            spec(requestSpecification)
            headers(requestHeaders)
            queryParams(requestQueryParams)
        } When {
            get(requestEndpoint)
        } Extract {
            response()
        }

    private fun post(): Response =
        Given {
            spec(requestSpecification)
            headers(requestHeaders)
            body(requestBody)
        } When {
            post(requestEndpoint)
        } Extract {
            response()
        }

    private fun put(): Response =
        Given {
            spec(requestSpecification)
            headers(requestHeaders)
            body(requestBody)
        } When {
            put(requestEndpoint)
        } Extract {
            response()
        }

    @Given("the endpoint {string}")
    fun `given the endpoint`(endpoint: String) {
        requestEndpoint = endpoint
    }

    @Given("the http headers")
    fun `given the http headers`(headers: DataTable) {
        requestHeaders = headers.asMap()
    }

    @Given("the query parameters")
    fun `given the query parameters`(queryParams: DataTable) {
        requestQueryParams = queryParams.asMap()
    }

    @Given("the json body")
    fun `given the json body`(body: String) {
        requestBody = body.trimIndent().replace("  ", "    ")
    }

    @When("I make a {string} http call")
    fun `when I make an http call`(method: String) {
        when (method) {
            "GET" -> get()
            "POST" -> post()
            "PUT" -> put()
            else -> throw IllegalArgumentException("$method http method is not supported")
        }.also {
            responseCode = it.statusCode
            responseBody = it.body.asPrettyString()
        }
    }

    @Then("response has {int} status code")
    fun `then response has status code`(statusCode: Int) {
        assertEquals(statusCode, responseCode)
    }

    @Then("response has body")
    fun `then response has body`(body: String) {
        assertEquals(body.trimIndent().replace("  ", "    "), responseBody)
    }

    @Then("response has no body")
    fun `then response has no body`() {
        assertEquals("", responseBody)
    }
}
