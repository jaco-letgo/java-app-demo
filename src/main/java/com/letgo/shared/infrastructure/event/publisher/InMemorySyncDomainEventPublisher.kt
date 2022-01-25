package com.letgo.shared.infrastructure.event.publisher

import com.letgo.shared.application.event.DomainEventPublisher
import com.letgo.shared.application.event.DomainEventSubscriber
import com.letgo.shared.domain.DomainEvent
import java.util.function.Consumer

class InMemorySyncDomainEventPublisher(
    private val subscribers: List<DomainEventSubscriber>
) : DomainEventPublisher {
    override fun publish(events: List<DomainEvent>) {
        events.forEach(Consumer { event: DomainEvent ->
            subscribers.forEach(Consumer { subscriber: DomainEventSubscriber ->
                tryToConsumeEvent(event, subscriber)
            })
        })
    }

    private fun tryToConsumeEvent(event: DomainEvent, subscriber: DomainEventSubscriber) {
        if (subscriber.isSubscribedTo(event)) {
            try {
                subscriber.consume(event)
            } catch (throwable: Throwable) {
                throwable.printStackTrace()
            }
        }
    }
}
