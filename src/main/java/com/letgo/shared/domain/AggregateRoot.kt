package com.letgo.shared.domain

@DomainObject
abstract class AggregateRoot {
    private val events: MutableList<DomainEvent> = mutableListOf()

    fun retrieveEvents(): List<DomainEvent> = events.apply { clear() }.run { toList() }

    protected fun storeEvent(event: DomainEvent) {
        events.add(event)
    }
}
