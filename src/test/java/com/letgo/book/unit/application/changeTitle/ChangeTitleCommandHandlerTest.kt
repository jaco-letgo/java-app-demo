package com.letgo.book.unit.application.changeTitle

import com.letgo.book.application.changeTitle.ChangeTitleCommand
import com.letgo.book.application.changeTitle.ChangeTitleCommandHandler
import com.letgo.book.domain.BookFinder
import com.letgo.book.domain.BookStatus
import com.letgo.book.domain.BookTitleChanged
import com.letgo.book.unit.application.BookTestCase
import com.letgo.book.unit.domain.ABookTitle
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

private class ChangeTitleCommandHandlerTest : BookTestCase() {
    private val handler = ChangeTitleCommandHandler(repository, BookFinder(repository), publisher)

    @Test
    fun `It should change an existing book title`() {
        val currentBook = anExistingBook()
        val currentBookId = currentBook.id()
        val newTitle = ABookTitle.random()
        val updatedBook = currentBook.copy(
            title = newTitle,
            status = BookStatus.Edited,
        )

        expectDomainEventsToBePublished(
            BookTitleChanged(
                aggregateId = currentBookId.value(),
                oldTitle = currentBook.title().value(),
                newTitle = newTitle.value(),
                occurredOn = newTitle.createdAt(),
            )
        )

        handler.handle(
            ChangeTitleCommand(
                id = currentBookId.value(),
                newTitle = newTitle.value(),
                occurredOn = newTitle.createdAt().toString(),
            )
        )

        assertEquals(updatedBook, repository.find(currentBookId))

        eventsShouldBePublished()
    }

    @Test
    fun `It should not apply earlier changes`() {
        val currentBook = anExistingBook()
        val currentBookId = currentBook.id()
        val currentBookTitle = currentBook.title()
        val earlierTitle = ABookTitle.with(
            title = currentBook.title().value() + "olakease",
            createdAt = currentBookTitle.createdAt().minusHours(1),
        )

        expectDomainEventsToBePublished()

        handler.handle(
            ChangeTitleCommand(
                id = currentBookId.value(),
                newTitle = earlierTitle.value(),
                occurredOn = earlierTitle.createdAt().toString(),
            )
        )

        assertEquals(currentBook, repository.find(currentBookId))

        eventsShouldNotBePublished()
    }

    @Test
    fun `It should be idempotent`() {
        val currentBook = anExistingBook()
        val currentBookId = currentBook.id()
        val currentTitle = currentBook.title()

        expectDomainEventsToBePublished()

        handler.handle(
            ChangeTitleCommand(
                id = currentBookId.value(),
                newTitle = currentTitle.value(),
                occurredOn = currentTitle.createdAt().toString(),
            )
        )

        assertEquals(currentBook, repository.find(currentBookId))

        eventsShouldNotBePublished()
    }
}
