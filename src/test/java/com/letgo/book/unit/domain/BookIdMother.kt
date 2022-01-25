package com.letgo.book.unit.domain

import com.letgo.book.domain.BookId.Companion.create
import com.letgo.book.domain.BookId

object BookIdMother {
    @JvmStatic
    fun create(id: String): BookId {
        return BookId.create(id)
    }

    @JvmStatic
    fun random(): BookId {
        return create()
    }
}
