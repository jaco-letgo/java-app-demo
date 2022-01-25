package com.letgo.book.domain

import java.io.Serializable
import java.util.*

class BookId private constructor(
    private val value: UUID
) : Serializable {
    fun value(): String {
        return value.toString()
    }

    override fun equals(other: Any?): Boolean {
        return other is BookId && value() == other.value()
    }

    override fun hashCode(): Int {
        return value().hashCode()
    }

    companion object {
        @JvmStatic
        fun create(): BookId {
            return BookId(UUID.randomUUID())
        }

        @JvmStatic
        fun create(value: String): BookId {
            return BookId(UUID.fromString(value))
        }
    }
}
