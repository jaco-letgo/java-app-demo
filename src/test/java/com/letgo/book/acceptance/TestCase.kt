package com.letgo.book.acceptance

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.*

private const val DOMAIN_NAME = "http://localhost:"

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
abstract class TestCase {
    @LocalServerPort
    private var port = 0

    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    protected fun get(endpoint: String): ResponseEntity<String> =
        restTemplate.getForEntity(apiEndpoint(endpoint), String::class.java)

    protected fun post(body: String): ResponseEntity<String> {
        val headers = HttpHeaders().also {
            it.contentType = MediaType.APPLICATION_JSON
        }
        return HttpEntity(body, headers).run {
            restTemplate.postForEntity(apiEndpoint("/book"), this, String::class.java)
        }
    }

    protected fun put(url: String): ResponseEntity<String> =
        restTemplate.exchange(url, HttpMethod.PUT, HttpEntity.EMPTY, String::class.java)

    protected fun givenAnExistingBookWith(id: String, title: String) {
        post("""{"id": $id, "title": $title}""")
        weWaitForMessagesToBeProcessed()
    }

    protected fun weWaitForMessagesToBeProcessed() = Thread.sleep(1000)

    private fun apiEndpoint(endpoint: String) = DOMAIN_NAME + port + endpoint
}
