package com.letgo.book.domain

import java.time.LocalDateTime
import java.util.*

class BookTitle private constructor(
    private val value: String,
    private val createdAt: LocalDateTime
) {
    fun value(): String {
        return value
    }

    fun createdAt(): LocalDateTime {
        return createdAt
    }

    override fun equals(other: Any?): Boolean {
        return (other is BookTitle
                && value() == other.value()
                && createdAt() == other.createdAt()
                )
    }

    override fun hashCode(): Int {
        return Objects.hash(value, createdAt)
    }

    fun isNewerThan(title: BookTitle): Boolean {
        return createdAt.isAfter(title.createdAt())
    }

    companion object {
        @JvmStatic
        fun create(value: String): BookTitle {
            return BookTitle(value, LocalDateTime.now())
        }

        @JvmStatic
        fun create(value: String, createdAt: LocalDateTime): BookTitle {
            return BookTitle(value, createdAt)
        }
    }
}
