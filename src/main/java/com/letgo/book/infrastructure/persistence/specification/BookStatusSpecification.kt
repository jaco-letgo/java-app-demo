package com.letgo.book.infrastructure.persistence.specification

import com.letgo.book.domain.Book
import com.letgo.book.domain.BookStatus
import com.letgo.book.domain.criteria.BookStatusFilter
import com.letgo.shared.domain.criteria.Operator
import com.letgo.shared.infrastructure.persistance.specification.Specification

class BookStatusSpecification(
    private val filter: BookStatusFilter,
) : Specification {
    override fun isSatisfiedBy(entity: Any): Boolean {
        return when (filter.operator) {
            Operator.Equal -> filter.value == if ((entity as Book).hasBeenEdited())
                BookStatus.Edited else BookStatus.Created
            else -> throw Exception("not defined operation")
        }
    }
}
