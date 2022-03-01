package com.letgo.shared.infrastructure.bus.event

import com.letgo.shared.application.bus.event.DomainEventSubscriber
import com.letgo.shared.domain.DomainEvent
import com.letgo.shared.infrastructure.InfrastructureService
import com.letgo.shared.infrastructure.bus.Consumer

@InfrastructureService
class InMemoryAsyncDomainEventConsumer(
    private val consumer: DomainEventAsyncConsumer,
    private val subscribers: List<DomainEventSubscriber>,
) : Consumer {
    override fun consume() {
        consumer.start { event ->
            subscribers.forEach { subscriber -> tryToConsumeEvent(event, subscriber) }
        }
    }

    private fun tryToConsumeEvent(event: DomainEvent, subscriber: DomainEventSubscriber) {
        if (subscriber.isSubscribedTo(event)) subscriber.consume(event)
    }
}
