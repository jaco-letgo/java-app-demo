package com.letgo.shared.application.event

import com.letgo.shared.domain.DomainEvent

interface DomainEventPublisher {
    fun publish(events: List<DomainEvent>)
}
