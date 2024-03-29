package com.letgo.book.infrastructure.event.subscriber

import com.letgo.book.domain.BookTitleChanged
import com.letgo.shared.application.bus.event.DomainEventSubscriber
import com.letgo.shared.domain.DomainEvent
import com.letgo.shared.infrastructure.InfrastructureService
import org.springframework.context.event.EventListener

@InfrastructureService
class PrintTitleChangeOnBookTitleChanged : DomainEventSubscriber {
    override fun isSubscribedTo(event: DomainEvent): Boolean = event is BookTitleChanged

    @EventListener(BookTitleChanged::class)
    override fun consume(event: DomainEvent) {
        if (event is BookTitleChanged) println("${event.oldTitle} -> ${event.newTitle}")
    }
}
