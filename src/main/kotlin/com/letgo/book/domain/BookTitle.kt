package com.letgo.book.domain

import com.letgo.shared.domain.DomainObject
import java.time.LocalDateTime

@DomainObject
data class BookTitle(
    private val value: String,
    private val createdAt: LocalDateTime,
) {
    constructor(value: String, createdAt: String) : this(value, LocalDateTime.parse(createdAt))
    constructor(value: String) : this(value, LocalDateTime.now())

    override fun toString(): String = value

    fun value(): String = value

    fun createdAt(): LocalDateTime = createdAt

    fun isNewerThan(title: BookTitle): Boolean = createdAt.isAfter(title.createdAt())
}
