package com.letgo.book.application

import com.letgo.shared.application.bus.query.QueryResponse

data class BooksResponse(
    val books: List<BookResponse>,
) : QueryResponse {
    constructor(vararg books: BookResponse) : this(books.toList())
}
