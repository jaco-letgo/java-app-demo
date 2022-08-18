package com.letgo.book.domain

object ABook {
    fun with(
        id: BookId = ABookId.random(),
        title: BookTitle = ABookTitle.random(),
        status: BookStatus = BookStatus.Created,
    ): Book = Book(id, status, title).apply { retrieveEvents() }

    fun with(
        id: String,
        title: String,
    ): Book = with(
        id = ABookId.with(id = id),
        title = ABookTitle.with(title = title),
    )

    fun edited(
        id: String,
        title: String,
    ): Book = with(
        id = ABookId.with(id = id),
        title = ABookTitle.with(title = title),
        status = BookStatus.Edited,
    )

    fun random(): Book = with()
}
