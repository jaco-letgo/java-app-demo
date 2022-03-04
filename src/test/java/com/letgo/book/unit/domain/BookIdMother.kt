package com.letgo.book.unit.domain

import com.letgo.book.domain.BookId
import java.util.UUID

object BookIdMother {
    fun create(
        id: String = UUID.randomUUID().toString(),
    ): BookId {
        return BookId(id)
    }

    fun random(): BookId {
        return create()
    }
}
