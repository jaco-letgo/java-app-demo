package com.letgo.book.infrastructure.persistence.specification

import com.letgo.book.domain.Book
import com.letgo.book.domain.BookStatus
import com.letgo.shared.domain.criteria.Filter
import com.letgo.shared.infrastructure.persistance.specification.Specification
import com.letgo.shared.infrastructure.persistance.specification.SpecificationFilterMapper

object BookStatusSpecificationFilterMapper : SpecificationFilterMapper {
    override fun uses(filter: Filter<*>): Boolean {
        return filter.value is BookStatus
    }

    override fun map(filter: Filter<*>): Specification<Book> {
        return BookStatusSpecification(filter as Filter<BookStatus>)
    }
}
