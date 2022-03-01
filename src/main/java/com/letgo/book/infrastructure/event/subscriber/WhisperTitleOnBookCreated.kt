package com.letgo.book.infrastructure.event.subscriber

import com.letgo.book.application.changeTitle.ChangeTitleCommand
import com.letgo.book.domain.BookCreated
import com.letgo.shared.application.bus.command.CommandBus
import com.letgo.shared.application.bus.event.DomainEventSubscriber
import com.letgo.shared.domain.DomainEvent
import com.letgo.shared.infrastructure.InfrastructureService
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import java.time.LocalDateTime
import java.util.*

@Async
@InfrastructureService
class WhisperTitleOnBookCreated(
    private val commandBus: CommandBus
) : DomainEventSubscriber {
    override fun isSubscribedTo(event: DomainEvent): Boolean = event is BookCreated

    @EventListener(BookCreated::class)
    override fun consume(event: DomainEvent) {
        if (event is BookCreated) {
            commandBus.dispatch(
                ChangeTitleCommand(
                    event.aggregateId(),
                    event.title().lowercase(Locale.getDefault()),
                    LocalDateTime.parse(event.occurredOn()).plusNanos(10000).toString()
                )
            )
        }
    }
}
