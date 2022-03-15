package com.letgo.book.application.find_by

import com.letgo.book.application.BookResponseMapper
import com.letgo.book.application.BooksResponse
import com.letgo.book.domain.BookId
import com.letgo.book.domain.BookRepository
import com.letgo.book.domain.BookStatus
import com.letgo.shared.application.bus.query.QueryHandler
import com.letgo.shared.domain.criteria.Criteria
import com.letgo.shared.domain.criteria.Filter
import com.letgo.shared.domain.criteria.FilterGroup

class FindBooksByCriteriaQueryHandler(
    private val repository: BookRepository,
    private val mapper: BookResponseMapper,
) : QueryHandler<FindBooksByCriteriaQuery, BooksResponse> {
    override fun handle(query: FindBooksByCriteriaQuery): BooksResponse {
        val criteria = Criteria.matchingAny(
            FilterGroup.withAll(
                Filter.equalTo(BookId(query.bookId)),
                Filter.equalTo(BookStatus.Created),
            ),
            FilterGroup.withAll(
                Filter.equalTo(BookStatus.Edited),
            )
        )
        val books = repository.findBy(criteria)
        return BooksResponse(books.map { mapper.map(it) })
    }
}
