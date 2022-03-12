package com.letgo.book.application.find

import com.letgo.book.application.BookResponseMapper
import com.letgo.book.domain.BookFinder
import com.letgo.book.domain.BookId
import com.letgo.shared.application.bus.query.QueryHandler

class FindBookQueryHandler(
    private val finder: BookFinder,
    private val mapper: BookResponseMapper,
) : QueryHandler<FindBookQuery, FindBookQueryResponse> {
    override fun handle(query: FindBookQuery): FindBookQueryResponse {
        return finder.find(BookId(query.id)).let { mapper.map(it) }
    }
}
