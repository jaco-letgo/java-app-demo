package com.letgo.book.infrastructure.persistence.specification

import com.letgo.book.domain.Book
import com.letgo.book.domain.BookId
import com.letgo.shared.domain.criteria.Filter
import com.letgo.shared.infrastructure.persistance.specification.Specification
import com.letgo.shared.infrastructure.persistance.specification.SpecificationFilterMapper

object BookIdSpecificationFilterMapper : SpecificationFilterMapper {
    override fun uses(filter: Filter<*>): Boolean {
        return filter.value is BookId
    }

    override fun map(filter: Filter<*>): Specification<Book> {
        return BookIdSpecification(filter as Filter<BookId>)
    }
}
