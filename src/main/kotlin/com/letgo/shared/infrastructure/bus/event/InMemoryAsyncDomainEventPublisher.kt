package com.letgo.shared.infrastructure.bus.event

import com.letgo.shared.application.bus.event.DomainEventPublisher
import com.letgo.shared.domain.DomainEvent

class InMemoryAsyncDomainEventPublisher(
    private val queue: DomainEventQueueHandler,
) : DomainEventPublisher {
    override fun publish(events: List<DomainEvent>) {
        events.forEach(queue.main::enqueue)
    }
}
