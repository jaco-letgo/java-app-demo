package com.letgo.book.domain

import com.letgo.shared.domain.DomainEvent
import java.time.LocalDateTime

class BookCreated(
    id: String,
    private val title: String,
    createdAt: LocalDateTime
) : DomainEvent(aggregateId = id, occurredOn = createdAt) {
    fun title(): String {
        return title
    }

    override fun name(): String {
        return "BookCreated"
    }

    override fun body(): String {
        return """
            {
                "title": $title
            }
            """
    }
}
