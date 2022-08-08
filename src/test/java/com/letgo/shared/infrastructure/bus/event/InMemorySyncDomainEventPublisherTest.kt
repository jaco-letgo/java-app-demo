package com.letgo.shared.infrastructure.bus.event

import com.letgo.shared.domain.AnEvent
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

private class InMemorySyncDomainEventPublisherTest {
    @Test
    fun `It should pass events to its subscribers`() {
        val anEvent = AnEvent()
        val subscriber = SpyDomainEventSubscriber(anEvent)
        val publisher = InMemorySyncDomainEventPublisher(listOf(subscriber))

        publisher.publish(listOf(anEvent))

        assertTrue(subscriber.hasBeenCalled())
    }
}
