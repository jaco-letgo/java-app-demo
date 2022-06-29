package com.letgo.book.application.find_containing_title

import com.letgo.book.application.BooksResponse
import com.letgo.book.application.toResponse
import com.letgo.book.domain.BookRepository
import com.letgo.book.domain.BookTitle
import com.letgo.shared.application.bus.query.QueryHandler
import com.letgo.shared.domain.criteria.Criteria
import com.letgo.shared.domain.criteria.Filter

class FindBooksContainingTitleQueryHandler(
    private val repository: BookRepository,
) : QueryHandler<FindBooksContainingTitleQuery, BooksResponse> {
    override fun handle(query: FindBooksContainingTitleQuery) =
        repository.findBy(
            Criteria.matching(Filter.containing("title", BookTitle(query.titleExcerpt)))
        ).toResponse()
}
