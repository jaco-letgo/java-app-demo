package com.letgo.book.acceptance

import com.letgo.book.domain.BookRepository
import com.letgo.book.unit.domain.BookMother
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity

private const val DOMAIN_NAME = "http://localhost:"

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
internal abstract class TestCase {
    @LocalServerPort
    private var port = 0

    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    @Autowired
    private lateinit var bookRepository: BookRepository

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
        bookRepository.save(BookMother.create(id = id, title = title))
    }

    protected fun weWaitForMessagesToBeProcessed() = Thread.sleep(1000)

    private fun apiEndpoint(endpoint: String) = DOMAIN_NAME + port + endpoint
}
