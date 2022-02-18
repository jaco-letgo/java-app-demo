package com.letgo.book.unit.domain

import com.letgo.book.domain.BookId
import java.util.*

object BookIdMother {
    fun create(
        id: String = UUID.randomUUID().toString()
    ): BookId {
        return BookId.create(id)
    }

    fun random(): BookId {
        return create()
    }
}
