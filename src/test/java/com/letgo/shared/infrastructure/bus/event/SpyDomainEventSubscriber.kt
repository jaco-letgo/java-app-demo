package com.letgo.shared.infrastructure.bus.event

import com.letgo.shared.application.bus.event.DomainEventSubscriber
import com.letgo.shared.domain.DomainEvent
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull

class SpyDomainEventSubscriber(
    private val expectedEvent: DomainEvent? = null
) : DomainEventSubscriber {
    private var hasBeenCalled = false

    override fun isSubscribedTo(event: DomainEvent): Boolean = true

    override fun consume(event: DomainEvent) {
        hasBeenCalled = true
        expectedEvent.let {
            assertNotNull(it)
            assertEquals(it!!.aggregateId(), event.aggregateId())
            assertEquals(it.name(), event.name())
            assertEquals(it.body(), event.body())
            assertEquals(it.occurredOn(), event.occurredOn())
        }
    }

    fun hasBeenCalled(): Boolean = hasBeenCalled
}
