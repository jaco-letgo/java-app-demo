package com.letgo.shared.infrastructure.bus.event

import com.letgo.shared.application.bus.event.DomainEventPublisher
import com.letgo.shared.domain.DomainEvent
import com.letgo.shared.infrastructure.InfrastructureService
import com.letgo.shared.infrastructure.bus.serialize.MessageSerializer

@InfrastructureService
class InMemoryAsyncSerializedDomainEventPublisher(
    private val queue: SerializedDomainEventQueueHandler,
    private val serializer: MessageSerializer<DomainEvent>,
) : DomainEventPublisher {
    override fun publish(events: List<DomainEvent>) {
        events.forEach { event ->
            queue.main.enqueue(serializer.serialize(event))
        }
    }
}
