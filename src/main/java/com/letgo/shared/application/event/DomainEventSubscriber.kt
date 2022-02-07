package com.letgo.shared.application.event

import com.letgo.shared.domain.DomainEvent

interface DomainEventSubscriber {
    fun isSubscribedTo(event: DomainEvent): Boolean
    fun consume(event: DomainEvent)
}
