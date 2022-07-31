package com.letgo.book.application.find_containing_title

import com.letgo.book.application.BookResponseMapper
import com.letgo.book.application.BooksResponse
import com.letgo.book.domain.BookRepository
import com.letgo.book.domain.criteria.BookTitleFilter
import com.letgo.shared.application.bus.query.QueryHandler
import com.letgo.shared.domain.criteria.Criteria

class FindBooksContainingTitleQueryHandler(
    private val repository: BookRepository,
    private val mapper: BookResponseMapper,
) : QueryHandler<FindBooksContainingTitleQuery, BooksResponse> {
    override fun handle(query: FindBooksContainingTitleQuery) =
        repository.findBy(
            Criteria.matching(BookTitleFilter.containing(query.titleExcerpt))
        ).map { mapper.map(it) }.let { BooksResponse(it) }
}
