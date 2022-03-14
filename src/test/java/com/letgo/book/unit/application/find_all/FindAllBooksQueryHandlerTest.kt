package com.letgo.book.unit.application.find_all

import com.letgo.book.application.BookResponse
import com.letgo.book.application.BookResponseMapper
import com.letgo.book.application.BooksResponse
import com.letgo.book.application.find_all.FindAllBooksQuery
import com.letgo.book.application.find_all.FindAllBooksQueryHandler
import com.letgo.book.unit.application.BookTestCase
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

private class FindAllBooksQueryHandlerTest : BookTestCase() {
    private val handler = FindAllBooksQueryHandler(repository, BookResponseMapper)

    @Test
    fun `It should return a list with all existing books`() {
        val anExistingBook = anExistingBook()
        val anotherExistingBook = anExistingBook()

        assertEquals(
            BooksResponse(
                BookResponse(
                    id = anExistingBook.id().value(),
                    title = anExistingBook.title().value(),
                    isEdited = anExistingBook.hasBeenEdited(),
                ),
                BookResponse(
                    id = anotherExistingBook.id().value(),
                    title = anotherExistingBook.title().value(),
                    isEdited = anotherExistingBook.hasBeenEdited(),
                )
            ),
            handler.handle(FindAllBooksQuery())
        )
    }

    @Test
    fun `It should return an empty list when there are no books`() {
        assertEquals(
            listOf<BookResponse>(),
            handler.handle(FindAllBooksQuery()).books
        )
    }
}
