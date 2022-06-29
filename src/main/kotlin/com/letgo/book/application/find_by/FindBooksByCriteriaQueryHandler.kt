package com.letgo.book.application.find_by

import com.letgo.book.application.BooksResponse
import com.letgo.book.application.toResponse
import com.letgo.book.domain.BookId
import com.letgo.book.domain.BookRepository
import com.letgo.book.domain.BookStatus
import com.letgo.shared.application.bus.query.QueryHandler
import com.letgo.shared.domain.criteria.Criteria
import com.letgo.shared.domain.criteria.FilterGroup
import com.letgo.shared.domain.criteria.Filter

class FindBooksByCriteriaQueryHandler(
    private val repository: BookRepository,
) : QueryHandler<FindBooksByCriteriaQuery, BooksResponse> {
    override fun handle(query: FindBooksByCriteriaQuery) =
        repository.findBy(
            Criteria.matchingAny(
                FilterGroup.withAll(
                    Filter.equalTo("id", BookId(query.bookId)),
                    Filter.equalTo("status", BookStatus.Edited)
                ),
                FilterGroup.withAll(
                    Filter.equalTo("status", BookStatus.Created)
                )
            )
        ).toResponse()
}
