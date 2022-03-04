package com.letgo.book.unit.application.create

import com.letgo.book.application.create.CreateBookCommand
import com.letgo.book.application.create.CreateBookCommandHandler
import com.letgo.book.domain.BookCreated
import com.letgo.book.unit.application.BookTestCase
import com.letgo.book.unit.domain.ABookId
import com.letgo.book.unit.domain.ABookTitle
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Test

class CreateBookCommandHandlerTest : BookTestCase() {
    private val handler = CreateBookCommandHandler(repository, publisher)

    @Test
    fun `It should create a book`() {
        val id = ABookId.random()
        val title = ABookTitle.random()

        expectDomainEventsToBePublished(
            BookCreated(
                aggregateId = id.value(),
                title = title.value(),
                occurredOn = title.createdAt(),
            )
        )

        handler.handle(
            CreateBookCommand(
                id = id.value(),
                title = title.value(),
                occurredOn = title.createdAt().toString(),
            )
        )

        repository.find(id)!!.run {
            assertEquals(id, id())
            assertEquals(title, title())
            assertFalse(hasBeenEdited())
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
            CreateBookCommand(
                id = currentBookId.value(),
                title = currentTitle.value(),
                occurredOn = currentTitle.createdAt().toString(),
            )
        )

        assertEquals(currentBook, repository.find(currentBookId))

        eventsShouldNotBePublished()
    }
}
