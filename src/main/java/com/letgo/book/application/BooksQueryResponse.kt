package com.letgo.book.application

import com.letgo.shared.application.bus.query.QueryResponse

data class BooksQueryResponse(
    val books: List<BookResponse>,
) : QueryResponse {
    constructor(vararg books: BookResponse) : this(books.toList())
}
