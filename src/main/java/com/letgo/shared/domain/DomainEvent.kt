package com.letgo.shared.domain

import java.time.LocalDateTime
import java.util.*

abstract class DomainEvent {
    private val id: UUID
    private val aggregateId: String
    private val occurredOn: LocalDateTime

    constructor(aggregateId: String) {
        id = UUID.randomUUID()
        this.aggregateId = aggregateId
        occurredOn = LocalDateTime.now()
    }

    constructor(aggregateId: String, id: UUID, occurredOn: LocalDateTime) {
        this.id = id
        this.aggregateId = aggregateId
        this.occurredOn = occurredOn
    }

    fun id(): String {
        return id.toString()
    }

    abstract fun name(): String
    fun aggregateId(): String {
        return aggregateId
    }

    fun occurredOn(): LocalDateTime {
        return occurredOn
    }

    abstract fun body(): String
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        val that = other as DomainEvent
        return (id == that.id
                && name() == that.name()
                && aggregateId == that.aggregateId
                && occurredOn == that.occurredOn
                && body() == that.body())
    }

    override fun hashCode(): Int {
        return Objects.hash(id, name(), aggregateId, occurredOn, body())
    }
}
