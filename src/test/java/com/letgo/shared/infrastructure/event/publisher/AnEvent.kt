package com.letgo.shared.infrastructure.event.publisher

import com.letgo.shared.domain.DomainEvent

class AnEvent : DomainEvent(aggregateId = "id") {
    override fun name(): String = "an_event"
    override fun body(): String = "{}"
}
