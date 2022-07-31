package com.letgo.book.application.find

import com.letgo.book.application.BookResponse
import com.letgo.book.application.toResponse
import com.letgo.book.domain.BookFinder
import com.letgo.book.domain.BookId
import com.letgo.shared.application.bus.query.QueryHandler

class FindBookQueryHandler(
    private val finder: BookFinder,
) : QueryHandler<FindBookQuery, BookResponse> {
    override fun handle(query: FindBookQuery) =
        finder.find(BookId(query.id)).toResponse()
}
