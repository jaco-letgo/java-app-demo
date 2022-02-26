package com.letgo.shared.domain

class AnEvent : DomainEvent(aggregateId = "id") {
    override fun name(): String = "an_event"
    override fun body(): String = "{}"
}
