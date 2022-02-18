package com.letgo.shared.infrastructure.event.publisher

import org.junit.jupiter.api.Assertions.*
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
