package com.letgo.book.unit.application.find

import com.letgo.book.application.find.FindBookQuery
import com.letgo.book.application.find.FindBookQueryHandler
import com.letgo.book.domain.BookFinder
import com.letgo.book.domain.BookNotFound
import com.letgo.book.unit.application.BookTestCase
import com.letgo.book.unit.domain.ABookId
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrowsExactly
import org.junit.jupiter.api.Test

class FindBookQueryHandlerTest : BookTestCase() {
    private val handler = FindBookQueryHandler(BookFinder(repository))

    @Test
    fun `It should find a book`() {
        val currentBook = anExistingBook()

        handler.handle(
            FindBookQuery(
                id = currentBook.id().value(),
            )
        ).run {
            assertEquals(currentBook.title().value(), title)
        }
    }

    @Test
    fun `It should throw an exception when book is not found`() {
        val id = ABookId.random().value()

        assertThrowsExactly(BookNotFound::class.java) {
            handler.handle(
                FindBookQuery(
                    id = id,
                )
            )
        }.run {
            assertEquals("Book not found with id $id", message)
        }
    }
}
