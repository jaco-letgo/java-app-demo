package com.letgo.stepdefinition

import com.letgo.api.Api
import com.letgo.book.domain.ABookId
import com.letgo.book.domain.Book
import com.letgo.book.domain.BookRepository
import com.letgo.book.domain.BookTitle
import com.letgo.book.domain.criteria.BookTitleFilter
import com.letgo.context.Context
import com.letgo.shared.domain.criteria.Criteria
import io.cucumber.datatable.DataTable
import io.cucumber.java.Before
import io.cucumber.java.en.And
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import io.restassured.response.Response
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import java.util.UUID
import javax.sql.DataSource

class BookStepDefinitions(
    dataSource: DataSource,
    private val context: Context,
    private val api: Api<Response>,
    private val repository: BookRepository,
) {
    private val connection = dataSource.connection

    @Before
    fun clearDatabase() {
        connection.prepareStatement("delete from books").execute()
    }

    @Given("the endpoint {string}")
    fun setEndpoint(endpoint: String) {
        context.set("endpoint", endpoint)
    }

    @And("the headers")
    fun setHeaders(headers: DataTable) {
        context.set("headers", headers.asMap())
    }

    @And("the body")
    fun setBody(body: String) {
        context.set("body", body)
    }

    @When("the user makes a POST call")
    fun callTheApp() {
        context.set(
            "response",
            api.post(
                endpoint = context.get<String>("endpoint").toString(),
                headers = context.get("headers") ?: emptyMap(),
                body = context.get<String>("body").toString()
            )
        )
    }

    @And("we wait for the messages to be processed")
    fun waitForMessagesToBeProcessed() {
        Thread.sleep(2000)
    }

    @Then("the user receives a {int} status code")
    fun assertStatusCode(statusCode: Int) {
        assertEquals(statusCode, context.get<Response>("response")?.statusCode)
    }

    @And("a book with id {string} is created")
    fun assertBookWithIdExists(id: String) {
        repository.find(ABookId.with(id)).also {
            assertNotNull(it)
            context.set("created_book", it)
        }
        connection.prepareStatement("select id from books where id=\"$id\"").executeQuery().apply {
            next()
        }.also {
            assertEquals(id, it.getString(1))
        }
    }

    @And("its title is {string}")
    fun assertBookHasTitle(title: String) {
        assertEquals(title, context.get<Book>("created_book")?.title()?.value())
        connection.prepareStatement("select title from books where title=\"$title\"").executeQuery().apply {
            next()
        }.also {
            assertEquals(title, it.getString(1))
        }
    }

    @Given("there is no book titled {string}")
    fun `given there is no book titled`(title: String) {
        repository.findBy(
            Criteria.matching(BookTitleFilter.equalTo(BookTitle(title)))
        ).also {
            assertTrue(it.isEmpty())
        }
        connection.prepareStatement("select title from books where title=\"$title\"").executeQuery().also {
            assertFalse(it.next())
        }
    }

    @When("the user creates a book titled {string}")
    fun `when the user creates a book titled`(title: String) {
        context.set("id", UUID.randomUUID().toString())
        setEndpoint("/books")
        context.set("headers", mapOf("Content-Type" to "application/json"))
        setBody(
            """
              {
                "id": "${context.get<String>("id")}",
                "title": "$title"
              }
            """
        )
        callTheApp()
        waitForMessagesToBeProcessed()
    }

    @Then("a book titled {string} is created")
    fun `then a book titled is created`(title: String) {
        assertStatusCode(201)
        assertBookWithIdExists(context.get("id") ?: "")
        assertBookHasTitle(title)
    }
}
