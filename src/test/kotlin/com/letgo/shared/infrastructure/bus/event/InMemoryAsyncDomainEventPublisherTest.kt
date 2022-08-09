package com.letgo.shared.infrastructure.bus.event

import com.letgo.shared.domain.AnEvent
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Test

private class InMemoryAsyncDomainEventPublisherTest {
    private val queueHandler = DomainEventQueueHandler(maxRetries = 0)
    private val queue = queueHandler.main
    private val eventPublisher = InMemoryAsyncDomainEventPublisher(queueHandler)

    @Test
    fun `It should enqueue a domain event`() {
        val event = AnEvent()

        eventPublisher.publish(listOf(event))

        assertFalse(queue.isEmpty)
        assertEquals(event, queue.peek())
    }
}
