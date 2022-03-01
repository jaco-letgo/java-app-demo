package com.letgo.shared.infrastructure.bus.event

import com.letgo.shared.application.bus.event.DomainEventSubscriber
import com.letgo.shared.domain.DomainEvent
import com.letgo.shared.infrastructure.InfrastructureService
import com.letgo.shared.infrastructure.bus.AsyncConsumer
import com.letgo.shared.infrastructure.bus.Consumer

@InfrastructureService
class InMemoryAsyncDomainEventConsumer(
    private val queueHandler: DomainEventQueueHandler,
    private val subscribers: List<DomainEventSubscriber>,
) : Consumer {
    override fun consume() {
        val consumer = AsyncConsumer(queueHandler)
        consumer.start { event ->
            subscribers.forEach { subscriber -> tryToConsumeEvent(event, subscriber) }
        }
    }

    private fun tryToConsumeEvent(event: DomainEvent, subscriber: DomainEventSubscriber) {
        if (subscriber.isSubscribedTo(event)) subscriber.consume(event)
    }
}
