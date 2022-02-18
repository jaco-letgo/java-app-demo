package com.letgo.shared.domain

import com.letgo.shared.infrastructure.event.publisher.AnEvent
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

    private class AnAggregate(event: DomainEvent) : AggregateRoot() {
        init {
            storeEvent(event)
        }
    }
}
