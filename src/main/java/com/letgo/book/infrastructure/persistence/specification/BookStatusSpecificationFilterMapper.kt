package com.letgo.book.infrastructure.persistence.specification

import com.letgo.book.domain.Book
import com.letgo.book.domain.criteria.BookStatusFilter
import com.letgo.shared.domain.criteria.Filter
import com.letgo.shared.infrastructure.persistance.specification.Specification
import com.letgo.shared.infrastructure.persistance.specification.SpecificationFilterMapper

object BookStatusSpecificationFilterMapper : SpecificationFilterMapper {
    override fun shouldMap(filter: Filter): Boolean {
        return filter is BookStatusFilter
    }

    override fun map(filter: Filter): Specification<Book> {
        return BookStatusSpecification(filter as BookStatusFilter)
    }
}
