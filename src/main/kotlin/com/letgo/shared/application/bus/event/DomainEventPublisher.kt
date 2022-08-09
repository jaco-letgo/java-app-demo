package com.letgo.shared.application.bus.event

import com.letgo.shared.domain.DomainEvent

interface DomainEventPublisher {
    fun publish(events: List<DomainEvent>)
}
