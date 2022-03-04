package com.letgo.shared.infrastructure.bus.event

import com.letgo.shared.application.bus.event.DomainEventSubscriber
import com.letgo.shared.infrastructure.bus.Consumer

class InMemoryAsyncDomainEventConsumer(
    private val consumer: DomainEventAsyncConsumer,
    private val subscribers: List<DomainEventSubscriber>,
) : Consumer {
    override fun consume() {
        consumer.start { event ->
            subscribers.filter { it.isSubscribedTo(event) }.forEach { it.consume(event) }
        }
    }
}
