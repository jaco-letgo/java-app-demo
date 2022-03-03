package com.letgo.shared.domain

import java.time.LocalDateTime
import java.util.UUID

data class AnEvent(
    override val id: UUID = UUID.randomUUID(),
    override val aggregateId: String = "id",
    override val occurredOn: LocalDateTime = LocalDateTime.now(),
) : DomainEvent
