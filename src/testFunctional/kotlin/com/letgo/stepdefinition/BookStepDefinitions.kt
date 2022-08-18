package com.letgo.stepdefinition

import com.letgo.book.domain.ABook
import com.letgo.book.domain.ABookId
import com.letgo.book.domain.Book
import com.letgo.book.domain.BookRepository
import io.cucumber.java.Before
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import java.sql.Connection
import javax.sql.DataSource

class BookStepDefinitions(
    dataSource: DataSource,
    private val repository: BookRepository,
) {
    private val connection: Connection = dataSource.connection
    private var actualBook: Book? = null
        get() = field ?: throw NoSuchElementException("missing actual book")

    @Before
    fun cleanEnvironment() {
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
        ABook.edited(
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
    }

    @Then("a book with id {string} does not exist")
    fun `then a book with id does not exist`(id: String) {
        assertNull(repository.find(ABookId.with(id)))
    }

    @Then("its title is {string}")
    fun `then its title is`(title: String) {
        assertEquals(title, actualBook!!.title().value())
    }

    @Then("it has been edited")
    fun `then it has been edited`() {
        assertTrue(actualBook!!.hasBeenEdited())
    }

    private fun Book.exists() {
        repository.save(this)
    }
}
