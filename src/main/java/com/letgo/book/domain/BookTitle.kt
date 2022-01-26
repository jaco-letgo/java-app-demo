package com.letgo.book.domain

import java.time.LocalDateTime

data class BookTitle private constructor(
    private val value: String,
    private val createdAt: LocalDateTime
) {
    fun value(): String {
        return value
    }

    fun createdAt(): LocalDateTime {
        return createdAt
    }

    fun isNewerThan(title: BookTitle): Boolean {
        return createdAt.isAfter(title.createdAt())
    }

    companion object {
        fun create(value: String): BookTitle {
            return BookTitle(value, LocalDateTime.now())
        }

        fun create(value: String, createdAt: LocalDateTime): BookTitle {
            return BookTitle(value, createdAt)
        }

        fun create(value: String, createdAt: String): BookTitle {
            return BookTitle(value, LocalDateTime.parse(createdAt))
        }
    }
}
