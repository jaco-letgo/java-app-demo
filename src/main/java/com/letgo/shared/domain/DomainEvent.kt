package com.letgo.shared.domain

import java.time.LocalDateTime
import java.util.*

abstract class DomainEvent(
    private val id: UUID = UUID.randomUUID(),
    private val aggregateId: String,
    private val occurredOn: LocalDateTime = LocalDateTime.now()
) {
    fun id(): String {
        return id.toString()
    }

    abstract fun name(): String

    fun aggregateId(): String {
        return aggregateId
    }

    fun occurredOn(): String {
        return occurredOn.toString()
    }

    abstract fun body(): String
}
