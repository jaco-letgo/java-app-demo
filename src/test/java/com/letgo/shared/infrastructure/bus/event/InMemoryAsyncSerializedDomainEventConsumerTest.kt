package com.letgo.shared.infrastructure.bus.event

import com.letgo.shared.application.bus.event.DomainEventSubscriber
import com.letgo.shared.domain.AnEvent
import com.letgo.shared.domain.DomainEvent
import com.letgo.shared.infrastructure.bus.serialize.MessageSerializer
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertSame
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.lang.Thread.sleep

private class InMemoryAsyncSerializedDomainEventConsumerTest {
    private val queueHandler = SerializedDomainEventQueueHandler(0)
    private val consumer = SerializedDomainEventAsyncConsumer(queueHandler)
    private val queue = queueHandler.main
    private val deadLetter = queueHandler.deadLetter
    private val serializer: MessageSerializer<DomainEvent> = mockk()

    @Test
    fun `It should dequeue, deserialize and pass a domain event into its subscribers`() {
        val serializedMessage = "olakease"
        val event = AnEvent()
        val handler = SpyDomainEventSubscriber(event)
        val consumer = InMemoryAsyncSerializedDomainEventConsumer(consumer, listOf(handler), serializer)

        queue.enqueue(serializedMessage)
        every { serializer.deserialize(serializedMessage) } answers { event }

        consumer.consume()
        sleep(100)

        assertTrue(queue.isEmpty)
        assertTrue(deadLetter.isEmpty)
        assertTrue(handler.hasBeenCalled())
    }

    @Test
    fun `It should send a message to the dead letter when a subscriber always throws an exception`() {
        val serializedMessage = "olakease"
        val handler = FailingDomainEventSubscriber()
        val consumer = InMemoryAsyncSerializedDomainEventConsumer(consumer, listOf(handler), serializer)

        queue.enqueue(serializedMessage)
        every { serializer.deserialize(serializedMessage) } answers { AnEvent() }

        consumer.consume()
        sleep(100)

        assertTrue(queue.isEmpty)
        assertFalse(deadLetter.isEmpty)
        assertSame(serializedMessage, deadLetter.peek())
    }

    private class FailingDomainEventSubscriber : DomainEventSubscriber {
        override fun isSubscribedTo(event: DomainEvent): Boolean = true

        override fun consume(event: DomainEvent) {
            throw Exception("something went wrong")
        }
    }
}
