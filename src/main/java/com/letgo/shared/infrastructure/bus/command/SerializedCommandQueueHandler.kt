package com.letgo.shared.infrastructure.bus.command

import com.letgo.shared.infrastructure.InfrastructureService
import com.letgo.shared.infrastructure.bus.queue.QueueHandler
import org.springframework.beans.factory.annotation.Value

@InfrastructureService
class SerializedCommandQueueHandler(
    @Value("\${bus.command.max_retries}") maxRetries: Int,
) : QueueHandler<String>(maxRetries)
