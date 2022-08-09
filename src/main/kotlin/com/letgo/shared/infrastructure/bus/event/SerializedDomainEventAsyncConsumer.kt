package com.letgo.shared.infrastructure.bus.event

import com.letgo.shared.infrastructure.InfrastructureService
import com.letgo.shared.infrastructure.bus.AsyncConsumer
import org.springframework.beans.factory.annotation.Value

@InfrastructureService
class SerializedDomainEventAsyncConsumer(
    queueHandler: SerializedDomainEventQueueHandler,
    @Value("\${bus.event.workers}") numberOfWorkers: Int = 1,
) : AsyncConsumer<String>(queueHandler, numberOfWorkers)
