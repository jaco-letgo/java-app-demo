package com.letgo.book.infrastructure.event.subscriber

import com.letgo.book.application.changeTitle.ChangeTitleCommand
import com.letgo.book.domain.BookCreated
import com.letgo.shared.application.bus.command.CommandBus
import com.letgo.shared.application.bus.event.DomainEventSubscriber
import com.letgo.shared.domain.DomainEvent
import com.letgo.shared.infrastructure.InfrastructureService
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import java.util.Locale

@Async
@InfrastructureService
class ShoutTitleOnBookCreated(
    private val commandBus: CommandBus
) : DomainEventSubscriber {
    override fun isSubscribedTo(event: DomainEvent): Boolean = event is BookCreated

    @EventListener(BookCreated::class)
    override fun consume(event: DomainEvent) {
        if (event is BookCreated) {
            commandBus.dispatch(
                ChangeTitleCommand(
                    event.aggregateId,
                    event.title.uppercase(Locale.getDefault()),
                    event.occurredOn.plusNanos(100000).toString(),
                )
            )
        }
    }
}
