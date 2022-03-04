package com.letgo.book.unit.domain

import com.letgo.book.domain.Book
import com.letgo.book.domain.BookId
import com.letgo.book.domain.BookTitle

object BookMother {
    fun create(
        id: BookId = BookIdMother.random(),
        title: BookTitle = BookTitleMother.random(),
    ): Book {
        val book = Book(id, title)
        book.retrieveEvents()
        return book
    }

    fun create(
        id: String,
        title: String,
    ): Book {
        return create(id = BookIdMother.create(id), title = BookTitleMother.create(title = title))
    }

    fun random(): Book {
        return create()
    }
}
