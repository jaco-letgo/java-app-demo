package com.letgo.shared.domain

import java.time.LocalDateTime
import java.util.*

abstract class DomainEvent(
    private val id: UUID = UUID.randomUUID(),
    private val aggregateId: String,
    private val occurredOn: LocalDateTime = LocalDateTime.now()
) {
    fun id(): String = id.toString()

    fun aggregateId(): String = aggregateId

    fun occurredOn(): String = occurredOn.toString()

    abstract fun name(): String

    abstract fun body(): String
}
