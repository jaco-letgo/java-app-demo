package com.letgo.shared.infrastructure.bus.event

import com.letgo.shared.domain.AnEvent
import com.letgo.shared.domain.DomainEvent
import com.letgo.shared.infrastructure.bus.serialize.MessageSerializer
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Test

private class InMemoryAsyncSerializedDomainEventPublisherTest {
    private val queueHandler = SerializedDomainEventQueueHandler(maxRetries = 0)
    private val queue = queueHandler.main
    private val serializer: MessageSerializer<DomainEvent> = mockk()
    private val eventPublisher = InMemoryAsyncSerializedDomainEventPublisher(queueHandler, serializer)

    @Test
    fun `It should serialize and enqueue a domain event`() {
        val serializedEvent = "OLAKEASE"
        val event = AnEvent()

        every { serializer.serialize(event) } answers { serializedEvent }

        eventPublisher.publish(listOf(event))

        assertFalse(queue.isEmpty)
        assertEquals(serializedEvent, queue.peek())
    }
}
