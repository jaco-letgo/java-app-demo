package com.letgo.book.infrastructure.persistence.specification

import com.letgo.book.domain.Book
import com.letgo.book.domain.BookStatus
import com.letgo.shared.domain.criteria.Filter
import com.letgo.shared.domain.criteria.Operator
import com.letgo.shared.infrastructure.persistance.specification.Specification

class BookStatusSpecification(
    private val filter: Filter<BookStatus>,
) : Specification<Book> {
    override fun isSatisfiedBy(entity: Book): Boolean {
        return when (filter.operator) {
            Operator.Equal -> filter.value == if (entity.hasBeenEdited())
                BookStatus.Edited else BookStatus.Created
            else -> throw Exception("not defined operation")
        }
    }
}
