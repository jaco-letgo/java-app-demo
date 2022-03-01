package com.letgo.shared.infrastructure.bus.command

import com.letgo.shared.infrastructure.InfrastructureService
import com.letgo.shared.infrastructure.bus.AsyncConsumer
import org.springframework.beans.factory.annotation.Value

@InfrastructureService
class SerializedCommandAsyncConsumer(
    queueHandler: SerializedCommandQueueHandler,
    @Value("\${bus.command.workers}") numberOfWorkers: Int = 1,
) : AsyncConsumer<String>(queueHandler, numberOfWorkers)
