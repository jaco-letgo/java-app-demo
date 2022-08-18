package com.letgo.book.domain.criteria

import com.letgo.book.domain.BookTitle
import com.letgo.shared.domain.criteria.Filter
import com.letgo.shared.domain.criteria.Operator

class BookTitleFilter private constructor(
    override val value: Any,
    override val operator: Operator,
) : Filter {
    override val name: String = "book_title"

    companion object {
        fun equalTo(bookTitle: BookTitle) = BookTitleFilter(
            value = bookTitle,
            operator = Operator.Equal,
        )

        fun containing(titleExcerpt: String) = BookTitleFilter(
            value = titleExcerpt,
            operator = Operator.Containing,
        )
    }
}
