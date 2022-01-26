package com.letgo.book.unit.domain

import com.letgo.book.domain.BookTitle
import java.time.LocalDateTime
import java.util.*

object BookTitleMother {
    @JvmStatic
    fun create(
        title: String = UUID.randomUUID().toString(),
        createdAt: LocalDateTime = LocalDateTime.now()
    ): BookTitle {
        return BookTitle.create(title, createdAt)
    }

    @JvmStatic
    fun random(): BookTitle {
        return create()
    }
}
