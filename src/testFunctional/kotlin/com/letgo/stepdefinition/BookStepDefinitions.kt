package com.letgo.stepdefinition

import com.letgo.api.RestAssuredApi
import com.letgo.book.domain.ABook
import com.letgo.book.domain.ABookId
import com.letgo.book.domain.ABookTitle
import com.letgo.book.domain.Book
import com.letgo.book.domain.BookRepository
import com.letgo.book.domain.BookTitle
import com.letgo.book.domain.criteria.BookTitleFilter
import com.letgo.shared.domain.criteria.Criteria
import io.cucumber.java.Before
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import javax.sql.DataSource

class BookStepDefinitions(
    dataSource: DataSource,
    private val api: RestAssuredApi,
    private val repository: BookRepository,
) {
    private val connection = dataSource.connection
    private var existingBook: Book = ABook.random()
    private var actualBook: Book? = null
        get() = field ?: throw NoSuchElementException("missing actual book")

    @Before
    fun clearDatabase() {
        connection.prepareStatement("delete from books").execute()
    }

    @Given("a book titled {string} with id {string}")
    fun `given a book with`(title: String, id: String) {
        ABook.with(
            id = id,
            title = title,
        ).exists()
    }

    @When("I wait for the messages to be processed")
    fun `when I wait for messages to be processed`() {
        Thread.sleep(2000)
    }

    @Then("a book with id {string} exists")
    fun `then a book with id exists`(id: String) {
        repository.find(ABookId.with(id)).also {
            assertNotNull(it)
            actualBook = it
        }
        connection.prepareStatement("select id from books where id=\"$id\"").executeQuery().apply {
            next()
        }.also {
            assertEquals(id, it.getString(1))
        }
    }

    @Then("a book with id {string} does not exist")
    fun `then a book with id does not exist`(id: String) {
        assertNull(repository.find(ABookId.with(id)))
        connection.prepareStatement("select id from books where id=\"$id\"").executeQuery().also {
            assertFalse(it.next())
        }
    }

    @Then("its title is {string}")
    fun `then its title is`(title: String) {
        assertEquals(title, actualBook!!.title().value())
        connection.prepareStatement("select title from books where title=\"$title\"").executeQuery().apply {
            next()
        }.also {
            assertEquals(title, it.getString(1))
        }
    }

    @Then("it has been edited")
    fun `then it has been edited`() {
        val book = actualBook!!
        assertTrue(book.hasBeenEdited())
        val id = book.id().value()
        connection.prepareStatement("select status from books where id=\"$id\"").executeQuery().apply {
            next()
        }.also {
            assertEquals(0, it.getInt(1))
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
        api.`given the endpoint`("/books")
        api.`given the json body`(
            """
                {
                    "id": "${existingBook.id().value()}",
                    "title": "$title"
                }
            """
        )
        api.`when I make an http call`("POST")
        `when I wait for messages to be processed`()
    }

    @Then("a book titled {string} is created")
    fun `then a book titled is created`(title: String) {
        api.`then response has status code`(201)
        `then a book with id exists`(existingBook.id().value())
        `then its title is`(title)
    }

    @Given("there is a book titled {string}")
    fun `given there is a book titled`(title: String) {
        ABook.with(
            title = ABookTitle.with(title)
        ).exists()
    }

    @When("the user changes its title to {string}")
    fun `when the user changes its title to`(title: String) {
        val id = existingBook.id()
        api.`given the endpoint`("/books/${id.value()}")
        api.`given the json body`(
            """
                {
                    "title": "$title"
                }
            """
        )
        api.`when I make an http call`("PUT")
        `when I wait for messages to be processed`()
    }

    @Then("the book is titled {string}")
    fun `then the book is titled`(title: String) {
        api.`then response has status code`(202)
        `then a book with id exists`(existingBook.id().value())
        `then its title is`(title)
        `then it has been edited`()
    }

    @When("the user finds a book with id {string}")
    fun `when the user finds a book with id`(id: String) {
        api.`given the endpoint`("/books/$id")
        api.`when I make an http call`("GET")
    }

    @Then("a book titled {string} is found")
    fun `then a book titled is found`(title: String) {
        api.`then response has status code`(200)
        api.`then response has body`(
            """
                {
                    "id": "6cdc1f93-f684-40c6-8581-c225e5c6bce6",
                    "title": "$title",
                    "isEdited": false
                }
            """.replace("    ", "  ")
        )
    }

    private fun Book.exists() {
        existingBook = this
        repository.save(this)
    }
}
