package com.letgo.book.application.find_all

import com.letgo.book.application.BooksResponse
import com.letgo.book.application.toResponse
import com.letgo.book.domain.BookRepository
import com.letgo.shared.application.bus.query.QueryHandler
import com.letgo.shared.domain.criteria.Criteria
import com.letgo.shared.domain.criteria.Pagination

class FindAllBooksQueryHandler(
    private val repository: BookRepository,
) : QueryHandler<FindAllBooksQuery, BooksResponse> {
    override fun handle(query: FindAllBooksQuery) =
        repository.findBy(
            Criteria.matchingEverything().paginatedBy(
                Pagination.ofPageSize(query.pageSize).showingPage(query.pageNumber)
            )
        ).toResponse()
}
