package com.letgo.shared.infrastructure.bus.event

import com.letgo.shared.application.bus.event.DomainEventPublisher
import com.letgo.shared.domain.DomainEvent
import com.letgo.shared.infrastructure.InfrastructureService
import org.springframework.context.ApplicationEventPublisher
import org.springframework.scheduling.annotation.EnableAsync

@EnableAsync
@InfrastructureService
class SpringAsyncDomainEventPublisher(
    private val publisher: ApplicationEventPublisher,
) : DomainEventPublisher {
    override fun publish(events: List<DomainEvent>) {
        events.forEach { publisher.publishEvent(it) }
    }
}
