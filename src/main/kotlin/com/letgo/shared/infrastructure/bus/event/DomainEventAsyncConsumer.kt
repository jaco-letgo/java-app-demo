package com.letgo.shared.infrastructure.bus.event

import com.letgo.shared.domain.DomainEvent
import com.letgo.shared.infrastructure.InfrastructureService
import com.letgo.shared.infrastructure.bus.AsyncConsumer
import org.springframework.beans.factory.annotation.Value

@InfrastructureService
class DomainEventAsyncConsumer(
    queueHandler: DomainEventQueueHandler,
    @Value("\${bus.event.workers}") numberOfWorkers: Int = 1,
) : AsyncConsumer<DomainEvent>(queueHandler, numberOfWorkers)
