package com.letgo.book.infrastructure.persistence.specification

import com.letgo.book.domain.Book
import com.letgo.book.domain.BookId
import com.letgo.shared.domain.criteria.Filter
import com.letgo.shared.domain.criteria.Operator
import com.letgo.shared.infrastructure.persistance.specification.Specification

class BookIdSpecification(
    private val filter: Filter<BookId>,
) : Specification<Book> {
    override fun isSatisfiedBy(entity: Book): Boolean {
        return when (filter.operator) {
            Operator.Equal -> filter.value == entity.id()
            else -> throw Exception("not defined operation")
        }
    }
}
