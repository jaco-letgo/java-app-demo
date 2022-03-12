package com.letgo.book.application.find_all

import com.letgo.book.application.BookResponseMapper
import com.letgo.book.domain.BookRepository
import com.letgo.shared.application.bus.query.QueryHandler

class FindAllBooksQueryHandler(
    private val repository: BookRepository,
    private val mapper: BookResponseMapper,
) : QueryHandler<FindAllBooksQuery, FindAllBooksQueryResponse> {
    override fun handle(query: FindAllBooksQuery): FindAllBooksQueryResponse {
        return repository.all().map { mapper.map(it) }.let { FindAllBooksQueryResponse(it) }
    }
}
