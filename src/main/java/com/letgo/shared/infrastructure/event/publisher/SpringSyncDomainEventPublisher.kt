package com.letgo.shared.infrastructure.event.publisher

import com.letgo.shared.application.event.DomainEventPublisher
import com.letgo.shared.domain.DomainEvent
import org.springframework.context.ApplicationEventPublisher
import java.util.function.Consumer

class SpringSyncDomainEventPublisher(
    private val publisher: ApplicationEventPublisher
) : DomainEventPublisher {
    override fun publish(events: List<DomainEvent>) {
        try {
            events.forEach(Consumer { event: DomainEvent -> publisher.publishEvent(event) })
        } catch (throwable: Throwable) {
            throwable.printStackTrace()
        }
    }
}
