package com.letgo.book.unit.application.find

import com.letgo.book.application.BookResponseMapper
import com.letgo.book.application.find.FindBookQuery
import com.letgo.book.application.find.FindBookQueryHandler
import com.letgo.book.application.find.FindBookQueryResponse
import com.letgo.book.domain.BookFinder
import com.letgo.book.domain.BookNotFound
import com.letgo.book.unit.application.BookTestCase
import com.letgo.book.unit.domain.ABookId
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrowsExactly
import org.junit.jupiter.api.Test

private class FindBookQueryHandlerTest : BookTestCase() {
    private val handler = FindBookQueryHandler(BookFinder(repository), BookResponseMapper)

    @Test
    fun `It should find a book`() {
        val currentBook = anExistingBook()
        val id = currentBook.id().value()

        assertEquals(
            FindBookQueryResponse(
                id = id,
                title = currentBook.title().value(),
                isEdited = currentBook.hasBeenEdited(),
            ),
            handler.handle(
                FindBookQuery(
                    id = id,
                )
            )
        )
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
