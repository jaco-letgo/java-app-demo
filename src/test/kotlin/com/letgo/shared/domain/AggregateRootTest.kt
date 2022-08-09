package com.letgo.shared.domain

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

private class AggregateRootTest {
    @Test
    fun `It should retrieve existing events and clear the event storage`() {
        val event = AnEvent()
        val aggregate = AnAggregate(event)

        assertEquals(listOf(event), aggregate.retrieveEvents())
        assertEquals(emptyList<DomainEvent>(), aggregate.retrieveEvents())
    }

    @Test
    fun `It should increase aggregate's version`() {
        val aggregate = AnAggregate()

        assertEquals(0, aggregate.version())

        aggregate.increaseVersion()

        assertEquals(1, aggregate.version())
    }

    private class AnAggregate(event: DomainEvent = AnEvent()) : AggregateRoot() {
        init {
            storeEvent(event)
        }
    }
}
