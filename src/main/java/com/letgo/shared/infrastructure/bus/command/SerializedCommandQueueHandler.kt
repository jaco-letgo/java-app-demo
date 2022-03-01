package com.letgo.shared.infrastructure.bus.command

import com.letgo.shared.infrastructure.InfrastructureService
import com.letgo.shared.infrastructure.bus.queue.QueueHandler

@InfrastructureService
class SerializedCommandQueueHandler : QueueHandler<String>()
