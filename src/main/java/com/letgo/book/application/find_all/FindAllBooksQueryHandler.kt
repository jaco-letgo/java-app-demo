package com.letgo.book.application.find_all

import com.letgo.book.application.BookResponseMapper
import com.letgo.book.application.BooksResponse
import com.letgo.book.domain.BookRepository
import com.letgo.shared.application.bus.query.QueryHandler
import com.letgo.shared.domain.criteria.Criteria
import com.letgo.shared.domain.criteria.Pagination

class FindAllBooksQueryHandler(
    private val repository: BookRepository,
    private val mapper: BookResponseMapper,
) : QueryHandler<FindAllBooksQuery, BooksResponse> {
    override fun handle(query: FindAllBooksQuery): BooksResponse {
        return repository.findBy(
            Criteria.matchingEverything().paginatedBy(
                Pagination.ofPageSize(query.pageSize).showingPage(query.pageNumber)
            )
        ).map { mapper.map(it) }.let { BooksResponse(it) }
    }
}
