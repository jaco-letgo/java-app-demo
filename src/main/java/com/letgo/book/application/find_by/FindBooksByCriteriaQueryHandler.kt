package com.letgo.book.application.find_by

import com.letgo.book.application.BookResponseMapper
import com.letgo.book.application.BooksResponse
import com.letgo.book.domain.BookId
import com.letgo.book.domain.BookRepository
import com.letgo.book.domain.BookStatus
import com.letgo.book.domain.criteria.BookIdFilter
import com.letgo.book.domain.criteria.BookStatusFilter
import com.letgo.shared.application.bus.query.QueryHandler
import com.letgo.shared.domain.criteria.Criteria
import com.letgo.shared.domain.criteria.FilterGroup

class FindBooksByCriteriaQueryHandler(
    private val repository: BookRepository,
    private val mapper: BookResponseMapper,
) : QueryHandler<FindBooksByCriteriaQuery, BooksResponse> {
    override fun handle(query: FindBooksByCriteriaQuery) =
        repository.findBy(
            Criteria.matchingAny(
                FilterGroup.withAll(
                    BookIdFilter.equalTo(BookId(query.bookId)),
                    BookStatusFilter.equalTo(BookStatus.Edited)
                ),
                FilterGroup.withAll(
                    BookStatusFilter.equalTo(BookStatus.Created)
                )
            )
        ).map { mapper.map(it) }.let { BooksResponse(it) }
}
