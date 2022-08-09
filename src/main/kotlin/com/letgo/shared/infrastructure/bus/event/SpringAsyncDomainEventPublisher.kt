package com.letgo.shared.infrastructure.bus.event

import com.letgo.shared.application.bus.event.DomainEventPublisher
import com.letgo.shared.domain.DomainEvent
import org.springframework.context.ApplicationEventPublisher
import org.springframework.scheduling.annotation.EnableAsync

@EnableAsync
class SpringAsyncDomainEventPublisher(
    private val publisher: ApplicationEventPublisher,
) : DomainEventPublisher {
    override fun publish(events: List<DomainEvent>) {
        events.forEach { publisher.publishEvent(it) }
    }
}
