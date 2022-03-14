package com.letgo.book.unit.application.find_by

import com.letgo.book.application.BookResponse
import com.letgo.book.application.BookResponseMapper
import com.letgo.book.application.BooksResponse
import com.letgo.book.application.find_by.FindBooksByCriteriaQuery
import com.letgo.book.application.find_by.FindBooksByCriteriaQueryHandler
import com.letgo.book.domain.BookStatus
import com.letgo.book.unit.application.BookTestCase
import com.letgo.book.unit.domain.ABook
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

private class FindBooksByCriteriaQueryHandlerTest : BookTestCase() {
    private val handler = FindBooksByCriteriaQueryHandler(repository, BookResponseMapper)

    @Test
    fun `It should return a list with criteria specified books`() {
        val createdBookWithConcreteId = anExistingBook()
        val nonMatchingCriteriaBook = anExistingBook()
        val editedBook = ABook.random().copy(status = BookStatus.Edited).also { repository.save(it) }

        val response = handler.handle(
            FindBooksByCriteriaQuery(
                createdBookWithConcreteId.id().value()
            )
        )
        assertEquals(
            BooksResponse(
                BookResponse(
                    id = createdBookWithConcreteId.id().value(),
                    title = createdBookWithConcreteId.title().value(),
                    isEdited = createdBookWithConcreteId.hasBeenEdited(),
                ),
                BookResponse(
                    id = editedBook.id().value(),
                    title = editedBook.title().value(),
                    isEdited = editedBook.hasBeenEdited(),
                )
            ),
            response
        )
    }
}
