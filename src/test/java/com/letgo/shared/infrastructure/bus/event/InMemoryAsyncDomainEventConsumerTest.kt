package com.letgo.shared.infrastructure.bus.event

import com.letgo.shared.application.bus.event.DomainEventSubscriber
import com.letgo.shared.domain.AnEvent
import com.letgo.shared.domain.DomainEvent
import com.letgo.shared.infrastructure.bus.queue.Queue
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertSame
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

private class InMemoryAsyncDomainEventConsumerTest {
    private val queueHandler = DomainEventQueueHandler(0)
    private val consumer = DomainEventAsyncConsumer(queueHandler)
    private val queue: Queue<DomainEvent> = queueHandler.main
    private val deadLetter: Queue<DomainEvent> = queueHandler.deadLetter

    @Test
    fun `It should dequeue and pass an event into its subscribers`() {
        val event = AnEvent()
        val subscriber = SpyDomainEventSubscriber(event)
        val consumer = InMemoryAsyncDomainEventConsumer(consumer, listOf(subscriber))

        queue.enqueue(event)

        consumer.consume()
        Thread.sleep(100)

        assertTrue(queue.isEmpty)
        assertTrue(deadLetter.isEmpty)
        assertTrue(subscriber.hasBeenCalled())
    }

    @Test
    fun `It should send a message to the dead letter when a subscriber always throws an exception`() {
        val event = AnEvent()
        val subscriber = FailingDomainEventSubscriber()
        val consumer = InMemoryAsyncDomainEventConsumer(consumer, listOf(subscriber))

        queue.enqueue(event)

        consumer.consume()
        Thread.sleep(100)

        assertTrue(queue.isEmpty)
        assertFalse(deadLetter.isEmpty)
        assertSame(event, deadLetter.peek())
    }

    private class FailingDomainEventSubscriber : DomainEventSubscriber {
        override fun isSubscribedTo(event: DomainEvent): Boolean = true

        override fun consume(event: DomainEvent) {
            throw Exception("something went wrong")
        }
    }
}
