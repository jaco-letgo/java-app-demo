package com.letgo.book.unit.application.find_containing_title

import com.letgo.book.application.BookResponse
import com.letgo.book.application.BookResponseMapper
import com.letgo.book.application.BooksResponse
import com.letgo.book.application.find_containing_title.FindBooksContainingTitleQuery
import com.letgo.book.application.find_containing_title.FindBooksContainingTitleQueryHandler
import com.letgo.book.unit.application.BookTestCase
import com.letgo.book.unit.domain.ABook
import com.letgo.book.unit.domain.ABookTitle
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

private class FindBooksContainingTitleQueryHandlerTest : BookTestCase() {
    private val handler = FindBooksContainingTitleQueryHandler(repository, BookResponseMapper)

    @Test
    fun `It should return a list with criteria specified books`() {
        val book1 = aBookWithTitle("aaaa")
        val book2 = aBookWithTitle("ccac")
        val book3 = aBookWithTitle("ddd a")

        aBookWithTitle("bbAb")
        aBookWithTitle("bbbb")

        val response = handler.handle(
            FindBooksContainingTitleQuery("a")
        )
        assertEquals(
            BooksResponse(
                BookResponse(
                    id = book1.id().value(),
                    title = book1.title().value(),
                    isEdited = book1.hasBeenEdited(),
                ),
                BookResponse(
                    id = book2.id().value(),
                    title = book2.title().value(),
                    isEdited = book2.hasBeenEdited(),
                ),
                BookResponse(
                    id = book3.id().value(),
                    title = book3.title().value(),
                    isEdited = book3.hasBeenEdited(),
                ),
            ),
            response
        )
    }

    private fun aBookWithTitle(title: String) =
        ABook.with(title = ABookTitle.with(title = title)).also { repository.save(it) }
}
