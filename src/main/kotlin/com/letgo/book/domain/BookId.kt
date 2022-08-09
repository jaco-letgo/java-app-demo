package com.letgo.book.domain

import java.io.Serializable
import java.util.UUID

data class BookId(
    private val value: UUID,
) : Serializable {
    constructor(value: String) : this(UUID.fromString(value))

    fun value(): String {
        return value.toString()
    }
}
