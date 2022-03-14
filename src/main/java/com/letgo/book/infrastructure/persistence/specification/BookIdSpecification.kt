package com.letgo.book.infrastructure.persistence.specification

import com.letgo.book.domain.Book
import com.letgo.book.domain.criteria.BookIdFilter
import com.letgo.shared.domain.criteria.Operator
import com.letgo.shared.infrastructure.persistance.specification.Specification

class BookIdSpecification(
    private val filter: BookIdFilter,
) : Specification {
    override fun isSatisfiedBy(entity: Any): Boolean {
        return when (filter.operator) {
            Operator.Equal -> filter.value == (entity as Book).id()
            else -> throw Exception("not defined operation")
        }
    }
}
