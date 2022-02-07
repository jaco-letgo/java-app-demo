package com.letgo.book.unit.application.changeTitle

import com.letgo.book.application.changeTitle.ChangeTitleCommand
import com.letgo.book.application.changeTitle.ChangeTitleCommandHandler
import com.letgo.book.domain.BookFinder
import com.letgo.book.domain.BookTitleChanged
import com.letgo.book.unit.application.BookTestCase
import com.letgo.book.unit.domain.BookTitleMother
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class ChangeTitleCommandHandlerTest : BookTestCase() {
    private val handler = ChangeTitleCommandHandler(repository, BookFinder(repository), publisher)

    @Test
    fun `It should change an existing book title`() {
        val currentBook = anExistingBook()
        val id = currentBook.id()
        val newTitle = BookTitleMother.random()
        expectDomainEventsToBePublished(
            BookTitleChanged(
                id.value(),
                currentBook.title().value(),
                newTitle.value(),
                newTitle.createdAt()
            )
        )
        handler.handle(ChangeTitleCommand(id.value(), newTitle.value(), newTitle.createdAt().toString()))
        repository.find(id).orElseThrow().run {
            assertEquals(id, this.id())
            assertEquals(newTitle, this.title())
            assertTrue(this.hasBeenEdited())
        }
        eventsShouldBePublished()
    }

    @Test
    fun `It should be idempotent`() {
        val currentBook = anExistingBook()
        val id = currentBook.id()
        val title = currentBook.title()
        expectDomainEventsToBePublished()
        handler.handle(ChangeTitleCommand(id.value(), title.value(), title.createdAt().toString()))
        assertEquals(currentBook, repository.find(id).orElseThrow())
        eventsShouldNotBePublished()
    }
}
