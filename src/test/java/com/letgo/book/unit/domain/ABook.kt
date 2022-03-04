package com.letgo.book.unit.domain

import com.letgo.book.domain.Book
import com.letgo.book.domain.BookId
import com.letgo.book.domain.BookTitle

object ABook {
    fun with(
        id: BookId = ABookId.random(),
        title: BookTitle = ABookTitle.random(),
    ): Book {
        val book = Book(id, title)
        book.retrieveEvents()
        return book
    }

    fun with(
        id: String,
        title: String,
    ): Book {
        return with(id = ABookId.with(id = id), title = ABookTitle.with(title = title))
    }

    fun random(): Book = with()
}
