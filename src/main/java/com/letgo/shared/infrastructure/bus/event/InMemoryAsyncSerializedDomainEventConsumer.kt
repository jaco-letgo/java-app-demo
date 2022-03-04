package com.letgo.shared.infrastructure.bus.event

import com.letgo.shared.application.bus.event.DomainEventSubscriber
import com.letgo.shared.domain.DomainEvent
import com.letgo.shared.infrastructure.InfrastructureService
import com.letgo.shared.infrastructure.bus.Consumer
import com.letgo.shared.infrastructure.bus.serialize.MessageSerializer

@InfrastructureService
class InMemoryAsyncSerializedDomainEventConsumer(
    private val consumer: SerializedDomainEventAsyncConsumer,
    private val subscribers: List<DomainEventSubscriber>,
    private val serializer: MessageSerializer<DomainEvent>,
) : Consumer {
    override fun consume() {
        consumer.start { message ->
            serializer.deserialize(message).also { event ->
                subscribers.filter { it.isSubscribedTo(event) }.forEach { it.consume(event) }
            }
        }
    }
}
