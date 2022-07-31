package com.letgo.book.unit.application.find_by

import com.letgo.book.application.BookResponse
import com.letgo.book.application.BooksResponse
import com.letgo.book.application.find_by.FindBooksByCriteriaQuery
import com.letgo.book.application.find_by.FindBooksByCriteriaQueryHandler
import com.letgo.book.domain.BookStatus
import com.letgo.book.unit.application.BookTestCase
import com.letgo.book.unit.domain.ABook
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

private class FindBooksByCriteriaQueryHandlerTest : BookTestCase() {
    private val handler = FindBooksByCriteriaQueryHandler(repository)

    @Test
    fun `It should return a list with criteria specified books`() {
        val editedBookWithConcreteId = givenAnExistingEditedBook()
        givenAnExistingEditedBook()
        givenAnExistingEditedBook()
        val createdBook = anExistingBook()

        val response = handler.handle(
            FindBooksByCriteriaQuery(
                editedBookWithConcreteId.id().value()
            )
        )
        assertEquals(
            BooksResponse(
                BookResponse(
                    id = editedBookWithConcreteId.id().value(),
                    title = editedBookWithConcreteId.title().value(),
                    isEdited = editedBookWithConcreteId.hasBeenEdited(),
                ),
                BookResponse(
                    id = createdBook.id().value(),
                    title = createdBook.title().value(),
                    isEdited = createdBook.hasBeenEdited(),
                )
            ),
            response
        )
    }

    private fun givenAnExistingEditedBook() = ABook.random().copy(status = BookStatus.Edited).also {
        repository.save(it)
    }
}
