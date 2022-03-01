package com.letgo.shared.infrastructure.bus.event

import com.letgo.shared.domain.DomainEvent
import com.letgo.shared.infrastructure.InfrastructureService
import com.letgo.shared.infrastructure.bus.queue.QueueHandler

@InfrastructureService
class DomainEventQueueHandler : QueueHandler<DomainEvent>()
