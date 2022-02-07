package com.letgo.book.unit.application.create

import com.letgo.book.application.create.CreateBookCommand
import com.letgo.book.application.create.CreateBookCommandHandler
import com.letgo.book.domain.BookCreated
import com.letgo.book.unit.application.BookTestCase
import com.letgo.book.unit.domain.BookIdMother
import com.letgo.book.unit.domain.BookTitleMother
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Test

class CreateBookCommandHandlerTest : BookTestCase() {
    private val handler = CreateBookCommandHandler(repository, publisher)

    @Test
    fun `It should create a book`() {
        val id = BookIdMother.random()
        val title = BookTitleMother.random()
        expectDomainEventsToBePublished(BookCreated(id.value(), title.value(), title.createdAt()))
        handler.handle(CreateBookCommand(id.value(), title.value(), title.createdAt().toString()))
        repository.find(id).orElseThrow().run {
            assertEquals(id, this.id())
            assertEquals(title, this.title())
            assertFalse(this.hasBeenEdited())
        }
        eventsShouldBePublished()
    }

    @Test
    fun `It should be idempotent`() {
        val currentBook = anExistingBook()
        val id = currentBook.id()
        val title = currentBook.title()
        expectDomainEventsToBePublished()
        handler.handle(CreateBookCommand(id.value(), title.value(), title.createdAt().toString()))
        assertEquals(currentBook, repository.find(id).orElseThrow())
        eventsShouldNotBePublished()
    }
}
