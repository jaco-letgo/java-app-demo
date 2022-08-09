package com.letgo.book.application.find_all

import com.letgo.book.application.BookResponse
import com.letgo.book.application.BookTestCase
import com.letgo.book.application.BooksResponse
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

private class FindAllBooksQueryHandlerTest : BookTestCase() {
    private val handler = FindAllBooksQueryHandler(repository)

    @Test
    fun `It should return a list with all existing books`() {
        anExistingBook()
        anExistingBook()
        val anExistingBook = anExistingBook()
        val anotherExistingBook = anExistingBook()
        anExistingBook()
        anExistingBook()

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
            handler.handle(FindAllBooksQuery(2, 2))
        )
    }

    @Test
    fun `It should return an empty list when there are no books`() {
        assertEquals(
            emptyList<BookResponse>(),
            handler.handle(FindAllBooksQuery(100, 1)).books
        )
    }
}
