package com.letgo.shared.domain

import java.time.LocalDateTime
import java.util.UUID

interface DomainEvent {
    val id: UUID
    val aggregateId: String
    val occurredOn: LocalDateTime
    val type: String
}
