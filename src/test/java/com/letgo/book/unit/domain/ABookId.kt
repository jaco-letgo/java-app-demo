package com.letgo.book.unit.domain

import com.letgo.book.domain.BookId
import java.util.UUID

object ABookId {
    fun with(
        id: String = UUID.randomUUID().toString(),
    ): BookId = BookId(id)

    fun random(): BookId = with()
}
