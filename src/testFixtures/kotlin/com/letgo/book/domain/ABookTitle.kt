package com.letgo.book.domain

import java.time.LocalDateTime
import java.util.UUID

object ABookTitle {
    fun with(
        title: String = UUID.randomUUID().toString(),
        createdAt: LocalDateTime = LocalDateTime.now(),
    ): BookTitle = BookTitle(title, createdAt)

    fun random(): BookTitle = with()
}
