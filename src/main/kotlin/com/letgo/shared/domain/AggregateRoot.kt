package com.letgo.shared.domain

@DomainObject
abstract class AggregateRoot {
    private var version: Int = 0
    private val events: MutableList<DomainEvent> = mutableListOf()

    fun version(): Int = version

    fun retrieveEvents(): List<DomainEvent> = events.toList().also { events.clear() }

    fun increaseVersion() {
        version++
    }

    protected fun storeEvent(event: DomainEvent) {
        events.add(event)
    }
}
