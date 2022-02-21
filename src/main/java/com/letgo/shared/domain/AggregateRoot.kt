package com.letgo.shared.domain

@DomainObject
abstract class AggregateRoot {
    private val events: MutableList<DomainEvent> = mutableListOf()

    fun retrieveEvents(): List<DomainEvent> = events.toList().also { events.clear() }

    protected fun storeEvent(event: DomainEvent) {
        events.add(event)
    }
}
