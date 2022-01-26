package com.letgo.shared.infrastructure.event.publisher

import com.letgo.shared.application.event.DomainEventPublisher
import com.letgo.shared.domain.DomainEvent
import com.letgo.shared.infrastructure.InfrastructureService
import org.springframework.context.ApplicationEventPublisher
import org.springframework.scheduling.annotation.EnableAsync
import java.util.function.Consumer

@EnableAsync
@InfrastructureService
class SpringAsyncDomainEventPublisher(
    private val publisher: ApplicationEventPublisher
) : DomainEventPublisher {
    override fun publish(events: List<DomainEvent>) {
        try {
            events.forEach(Consumer { event: DomainEvent -> publisher.publishEvent(event) })
        } catch (throwable: Throwable) {
            throwable.printStackTrace()
            throw throwable
        }
    }
}
