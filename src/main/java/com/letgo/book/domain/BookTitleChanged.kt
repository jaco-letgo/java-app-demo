package com.letgo.book.domain

import com.letgo.shared.domain.DomainEvent
import java.time.LocalDateTime

class BookTitleChanged(
    id: String,
    private val oldTitle: String,
    private val newTitle: String,
    occurredOn: LocalDateTime
) : DomainEvent(aggregateId = id, occurredOn = occurredOn) {
    fun oldTitle(): String {
        return oldTitle
    }

    fun newTitle(): String {
        return newTitle
    }

    override fun name(): String {
        return "BookTitleChanged"
    }

    override fun body(): String {
        return "{'oldTitle': $oldTitle, 'newTitle': $newTitle}"
    }
}
