package com.letgo.book.application.find_by

import com.letgo.book.application.BooksResponse
import com.letgo.book.application.toResponse
import com.letgo.book.domain.BookId
import com.letgo.book.domain.BookRepository
import com.letgo.book.domain.BookStatus
import com.letgo.book.domain.criteria.Field
import com.letgo.shared.application.bus.query.QueryHandler
import com.letgo.shared.domain.criteria.Criteria
import com.letgo.shared.domain.criteria.Filter
import com.letgo.shared.domain.criteria.FilterGroup

class FindBooksByCriteriaQueryHandler(
    private val repository: BookRepository,
) : QueryHandler<FindBooksByCriteriaQuery, BooksResponse> {
    override fun handle(query: FindBooksByCriteriaQuery) =
        repository.findBy(
            Criteria.matchingAny(
                FilterGroup.withAll(
                    Filter.equalTo(Field.Id, BookId(query.bookId)),
                    Filter.equalTo(Field.Status, BookStatus.Edited),
                ),
                FilterGroup.withAll(
                    Filter.equalTo(Field.Status, BookStatus.Created),
                )
            )
        ).toResponse()
}
