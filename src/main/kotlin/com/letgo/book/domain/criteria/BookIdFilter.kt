package com.letgo.book.domain.criteria

import com.letgo.book.domain.BookId
import com.letgo.shared.domain.criteria.Filter
import com.letgo.shared.domain.criteria.Operator

class BookIdFilter(
    override val name: String = "book_id",
    override val value: Any,
    override val operator: Operator,
) : Filter {
    companion object {
        fun equalTo(bookId: BookId) = BookIdFilter(
            value = bookId,
            operator = Operator.Equal,
        )
    }
}
