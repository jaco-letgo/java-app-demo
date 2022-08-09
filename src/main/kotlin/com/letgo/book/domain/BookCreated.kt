package com.letgo.book.domain

import com.letgo.shared.domain.DomainEvent
import java.time.LocalDateTime
import java.util.UUID

data class BookCreated(
    override val aggregateId: String,
    val title: String,
    override val occurredOn: LocalDateTime,
    override val id: UUID = UUID.randomUUID(),
) : DomainEvent
