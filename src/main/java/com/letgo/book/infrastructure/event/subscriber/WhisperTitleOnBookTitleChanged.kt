package com.letgo.book.infrastructure.event.subscriber

import com.letgo.book.domain.BookTitleChanged
import com.letgo.shared.application.event.DomainEventSubscriber
import com.letgo.shared.domain.DomainEvent
import com.letgo.shared.infrastructure.InfrastructureService
import org.springframework.context.event.EventListener

@InfrastructureService
class WhisperTitleOnBookTitleChanged : DomainEventSubscriber {
    override fun isSubscribedTo(event: DomainEvent): Boolean {
        return event is BookTitleChanged
    }

    @EventListener(BookTitleChanged::class)
    override fun consume(event: DomainEvent) {
        if (event is BookTitleChanged) {
            println(event.oldTitle() + " -> " + event.newTitle())
        }
    }
}
