package com.letgo.book.unit.domain

import com.letgo.book.domain.BookTitle
import java.time.LocalDateTime

object BookTitleMother {
    @JvmStatic
    fun create(title: String): BookTitle {
        return BookTitle.create(title, LocalDateTime.now())
    }

    @JvmStatic
    fun create(title: String, createdAt: LocalDateTime): BookTitle {
        return BookTitle.create(title, createdAt)
    }

    @JvmStatic
    fun random(): BookTitle {
        return create("random")
    }
}
