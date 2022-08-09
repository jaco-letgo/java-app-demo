package com.letgo.book.domain

object ABook {
    fun with(
        id: BookId = ABookId.random(),
        title: BookTitle = ABookTitle.random(),
    ): Book = Book(id, title).apply { retrieveEvents() }

    fun with(
        id: String,
        title: String,
    ): Book = with(
        id = ABookId.with(id = id),
        title = ABookTitle.with(title = title),
    )

    fun random(): Book = with()
}