package com.letgo.book.domain

import com.letgo.shared.domain.DomainEvent
import java.time.LocalDateTime

class BookCreated(
    id: String,
    private val title: String,
    private val titleCreatedAt: LocalDateTime
) : DomainEvent(id) {
    fun title(): String {
        return title
    }

    fun titleCreatedAt(): LocalDateTime {
        return titleCreatedAt
    }

    override fun name(): String {
        return "BookCreated"
    }

    override fun body(): String {
        return "{'title': $title, 'titleCreatedAt': $titleCreatedAt}"
    }
}
