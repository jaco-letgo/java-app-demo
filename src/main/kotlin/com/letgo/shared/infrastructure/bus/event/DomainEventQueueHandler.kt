package com.letgo.shared.infrastructure.bus.event

import com.letgo.shared.domain.DomainEvent
import com.letgo.shared.infrastructure.InfrastructureService
import com.letgo.shared.infrastructure.bus.queue.QueueHandler
import org.springframework.beans.factory.annotation.Value

@InfrastructureService
class DomainEventQueueHandler(
    @Value("\${bus.event.max_retries}") maxRetries: Int,
) : QueueHandler<DomainEvent>(maxRetries)
