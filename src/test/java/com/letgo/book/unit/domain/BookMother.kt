package com.letgo.book.unit.domain

import com.letgo.book.domain.Book
import com.letgo.book.domain.BookId
import com.letgo.book.domain.BookTitle

object BookMother {
    fun create(
        id: BookId = BookIdMother.random(),
        title: BookTitle = BookTitleMother.random()
    ): Book {
        val book = Book.create(id, title)
        book.retrieveEvents()
        return book
    }

    fun random(): Book {
        return create()
    }
}
