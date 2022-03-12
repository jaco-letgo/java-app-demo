package com.letgo.book.application.find_all

import com.letgo.book.application.find.FindBookQueryResponse
import com.letgo.shared.application.bus.query.QueryResponse

data class FindAllBooksQueryResponse(
    val books: List<FindBookQueryResponse>,
) : QueryResponse {
    constructor(vararg books: FindBookQueryResponse) : this(books.toList())
}
