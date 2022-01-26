package com.letgo.book.domain

import java.io.Serializable
import java.util.*

data class BookId private constructor(
    private val value: UUID
) : Serializable {
    fun value(): String {
        return value.toString()
    }

    companion object {
        fun create(value: String): BookId {
            return BookId(UUID.fromString(value))
        }
    }
}
