package com.letgo.book.application.find

import com.letgo.book.domain.BookFinder
import com.letgo.book.domain.BookId
import com.letgo.shared.application.bus.query.QueryHandler

class FindBookQueryHandler(
    private val finder: BookFinder,
) : QueryHandler<FindBookQuery, FindBookQueryResponse> {
    override fun handle(query: FindBookQuery): FindBookQueryResponse {
        val book = finder.find(BookId(query.id))
        return FindBookQueryResponse(book.title().value())
    }
}
