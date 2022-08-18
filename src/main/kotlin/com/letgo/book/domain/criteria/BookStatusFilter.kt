package com.letgo.book.domain.criteria

import com.letgo.book.domain.BookStatus
import com.letgo.shared.domain.criteria.Filter
import com.letgo.shared.domain.criteria.Operator

class BookStatusFilter private constructor(
    override val value: Any,
    override val operator: Operator,
) : Filter {
    override val name: String = "book_status"

    companion object {
        fun equalTo(bookStatus: BookStatus) = BookStatusFilter(
            value = bookStatus,
            operator = Operator.Equal,
        )
    }
}
