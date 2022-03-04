package com.letgo.book.unit.domain

import com.letgo.book.domain.BookTitle
import java.time.LocalDateTime
import java.util.UUID

object BookTitleMother {
    fun create(
        title: String = UUID.randomUUID().toString(),
        createdAt: LocalDateTime = LocalDateTime.now(),
    ): BookTitle {
        return BookTitle(title, createdAt)
    }

    fun random(): BookTitle {
        return create()
    }
}
