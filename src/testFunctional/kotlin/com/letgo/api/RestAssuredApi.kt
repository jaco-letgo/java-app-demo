package com.letgo.api

import com.letgo.context.Context
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
import org.junit.jupiter.api.Assertions.*
import org.springframework.boot.test.web.server.LocalServerPort

class RestAssuredApi(
    private val context: Context,
) {
    @LocalServerPort
    private val port = 0
    lateinit var requestSpecification: RequestSpecification

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

    fun get(
        endpoint: String = requestEndpoint(),
        headers: Map<String, String> = requestHeaders(),
        queryParams: Map<String, String> = requestQueryParams()
    ): Response =
        Given {
            spec(requestSpecification)
            headers(headers)
        } When {
            get(endpoint, queryParams)
        } Extract {
            response()
        }

    fun post(
        endpoint: String = requestEndpoint(),
        headers: Map<String, String> = requestHeaders(),
        body: String = requestBody()
    ): Response =
        Given {
            spec(requestSpecification)
            headers(headers)
            body(body)
        } When {
            post(endpoint)
        } Extract {
            response()
        }

    fun put(
        endpoint: String = requestEndpoint(),
        headers: Map<String, String> = requestHeaders(),
        body: String = requestBody()
    ): Response =
        Given {
            spec(requestSpecification)
            headers(headers)
            body(body)
        } When {
            put(endpoint)
        } Extract {
            response()
        }

    @Given("the endpoint")
    fun `given the endpoint`(endpoint: String) {
        context.set("endpoint", endpoint)
    }

    @Given("the http headers")
    fun `given the http headers`(headers: DataTable) {
        context.set("headers", headers.asMap())
    }

    @Given("the query parameters")
    fun `given the query parameters`(queryParams: DataTable) {
        context.set("query_parameters", queryParams.asMap())
    }

    @Given("the json body")
    fun `given the json body`(body: String) {
        context.set("body", body.trimIndent().replace("  ", "    "))
    }

    @When("I make a {string} http call")
    fun `when I make an http call`(method: String) {
        when (method) {
            "GET" -> get()
            "POST" -> post()
            "PUT" -> put()
            else -> throw IllegalArgumentException("$method http method is not supported")
        }.also {
            context.set("response_code", it.statusCode)
            context.set("response_body", it.body.asPrettyString())
        }
    }

    @Then("response has {int} status code")
    fun `then response has status code`(statusCode: Int) {
        assertEquals(statusCode, responseCode())
    }

    @Then("response has body")
    fun `then response has body`(body: String) {
        assertEquals(body.trimIndent().replace("  ", "    "), responseBody())
    }

    @Then("response has no body")
    fun `then response has no body`() {
        assertEquals("", responseBody())
    }

    private fun requestEndpoint() = context.get("endpoint") ?: ""

    private fun requestHeaders(): Map<String, String> = context.get("headers") ?: emptyMap()

    private fun requestQueryParams(): Map<String, String> = context.get("query_params") ?: emptyMap()

    private fun requestBody() = context.get("body") ?: ""

    private fun responseCode(): String = context.get("response_code")
        ?: throw RuntimeException("no response from http call")

    private fun responseBody(): String = context.get("response_body") ?: ""
}
