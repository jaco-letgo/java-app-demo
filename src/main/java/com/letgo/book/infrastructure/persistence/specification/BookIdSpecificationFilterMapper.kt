package com.letgo.book.infrastructure.persistence.specification

import com.letgo.book.domain.criteria.BookIdFilter
import com.letgo.shared.domain.criteria.Filter
import com.letgo.shared.infrastructure.persistance.specification.Specification
import com.letgo.shared.infrastructure.persistance.specification.SpecificationFilterMapper

object BookIdSpecificationFilterMapper : SpecificationFilterMapper {
    override fun shouldMap(filter: Filter): Boolean {
        return filter is BookIdFilter
    }

    override fun map(filter: Filter): Specification {
        return BookIdSpecification(filter as BookIdFilter)
    }
}
