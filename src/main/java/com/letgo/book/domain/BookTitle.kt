package com.letgo.book.domain

import com.letgo.shared.domain.DomainObject
import java.time.LocalDateTime

@DomainObject
data class BookTitle(
    private val value: String,
    private val createdAt: LocalDateTime,
) {
    constructor(value: String, createdAt: String) : this(value, LocalDateTime.parse(createdAt))

    fun value(): String {
        return value
    }

    fun createdAt(): LocalDateTime {
        return createdAt
    }

    fun isNewerThan(title: BookTitle): Boolean {
        return createdAt.isAfter(title.createdAt())
    }
}
