package com.letgo.shared.infrastructure.bus.event

import com.letgo.shared.application.bus.event.DomainEventPublisher
import com.letgo.shared.application.bus.event.DomainEventSubscriber
import com.letgo.shared.domain.DomainEvent

class InMemorySyncDomainEventPublisher(
    private val subscribers: List<DomainEventSubscriber>,
) : DomainEventPublisher {
    override fun publish(events: List<DomainEvent>) {
        events.forEach { event -> subscribers.forEach { subscriber -> tryToConsumeEvent(event, subscriber) } }
    }

    private fun tryToConsumeEvent(event: DomainEvent, subscriber: DomainEventSubscriber) {
        if (subscriber.isSubscribedTo(event)) subscriber.consume(event)
    }
}
