package com.letgo.shared.domain

import java.time.LocalDateTime
import java.util.*

interface DomainEvent {
    val id: UUID
    val aggregateId: String
    val occurredOn: LocalDateTime
    val type: String get() = this::class.simpleName.toString()
}
