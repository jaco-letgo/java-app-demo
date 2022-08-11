package com.letgo.api

import io.cucumber.java.After
import io.cucumber.java.Before
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
import org.springframework.boot.test.web.server.LocalServerPort

class RestAssuredApi : Api<Response> {
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

    override fun get(endpoint: String, headers: Map<String, String>, queryParams: Map<String, String>): Response =
        Given {
            spec(requestSpecification)
            headers(headers)
        } When {
            get(endpoint, queryParams)
        } Extract {
            response()
        }

    override fun post(endpoint: String, headers: Map<String, String>, body: String): Response =
        Given {
            spec(requestSpecification)
            headers(headers)
            body(body)
        } When {
            post(endpoint)
        } Extract {
            response()
        }

    override fun put(endpoint: String, headers: Map<String, String>, body: String): Response =
        Given {
            spec(requestSpecification)
            headers(headers)
            body(body)
        } When {
            put(endpoint)
        } Extract {
            response()
        }

    override fun delete(endpoint: String, headers: Map<String, String>, body: String): Response =
        Given {
            spec(requestSpecification)
            headers(headers)
            body(body)
        } When {
            delete(endpoint)
        } Extract {
            response()
        }
}
