package com.letgo.shared.infrastructure.bus.event

import com.letgo.shared.application.bus.event.DomainEventSubscriber
import com.letgo.shared.domain.DomainEvent
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertInstanceOf

class SpyDomainEventSubscriber(
    private val expectedEvent: DomainEvent? = null,
) : DomainEventSubscriber {
    private var hasBeenCalled = false

    override fun isSubscribedTo(event: DomainEvent): Boolean = true

    override fun consume(event: DomainEvent) {
        hasBeenCalled = true
        expectedEvent?.let {
            assertInstanceOf(expectedEvent::class.java, event)
            assertEquals(it.aggregateId, event.aggregateId)
            assertEquals(it.occurredOn, event.occurredOn)
            assertEquals(it.type, event.type)
        } ?: assertEquals(expectedEvent, event)
    }

    fun hasBeenCalled(): Boolean = hasBeenCalled
}
