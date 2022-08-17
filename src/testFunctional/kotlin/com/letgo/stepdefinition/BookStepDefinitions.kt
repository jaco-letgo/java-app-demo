package com.letgo.stepdefinition

import com.letgo.book.domain.ABook
import com.letgo.book.domain.ABookId
import com.letgo.book.domain.Book
import com.letgo.book.domain.BookRepository
import com.letgo.book.domain.BookStatus
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

    @Given("an edited book titled {string} with id {string}")
    fun `given an edited book with`(title: String, id: String) {
        ABook.with(
            id = id,
            title = title,
        ).copy(status = BookStatus.Edited).exists()
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

    private fun Book.exists() {
        existingBook = this
        repository.save(this)
    }
}
