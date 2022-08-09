package com.letgo.book.domain

import com.letgo.shared.domain.DomainEvent
import java.time.LocalDateTime
import java.util.UUID

data class BookTitleChanged(
    override val aggregateId: String,
    val oldTitle: String,
    val newTitle: String,
    override val occurredOn: LocalDateTime,
    override val id: UUID = UUID.randomUUID(),
) : DomainEvent
