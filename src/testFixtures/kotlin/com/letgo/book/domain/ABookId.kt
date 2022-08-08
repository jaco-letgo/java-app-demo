package com.letgo.book.domain

import java.util.UUID

object ABookId {
    fun with(
        id: String = UUID.randomUUID().toString(),
    ): BookId = BookId(id)

    fun random(): BookId = with()
}
