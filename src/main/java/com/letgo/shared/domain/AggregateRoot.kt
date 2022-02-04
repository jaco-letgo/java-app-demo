package com.letgo.shared.domain

@DomainObject
abstract class AggregateRoot {
    private var events: MutableList<DomainEvent> = ArrayList()
    fun retrieveEvents(): List<DomainEvent> {
        val events: List<DomainEvent> = events
        this.events = ArrayList()
        return events
    }

    protected fun storeEvent(event: DomainEvent) {
        events.add(event)
    }
}
