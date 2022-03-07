package com.letgo.book.unit.application.changeTitle

import com.letgo.book.application.changeTitle.ChangeTitleCommand
import com.letgo.book.application.changeTitle.ChangeTitleCommandHandler
import com.letgo.book.domain.BookFinder
import com.letgo.book.domain.BookTitleChanged
import com.letgo.book.unit.application.BookTestCase
import com.letgo.book.unit.domain.ABookTitle
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class ChangeTitleCommandHandlerTest : BookTestCase() {
    private val finder = BookFinder(repository)
    private val handler = ChangeTitleCommandHandler(repository, finder, publisher)

    @Test
    fun `It should change an existing book title`() {
        val currentBook = anExistingBook()
        val currentBookId = currentBook.id()
        val newTitle = ABookTitle.random()

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

        finder.find(currentBookId).run {
            assertEquals(currentBookId, id())
            assertEquals(newTitle, title())
            assertTrue(hasBeenEdited())
        }

        eventsShouldBePublished()
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
