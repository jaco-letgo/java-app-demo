package com.letgo.book.infrastructure.event.subscriber;

import com.letgo.book.application.changeTitle.ChangeTitleCommand;
import com.letgo.book.domain.BookCreated;
import com.letgo.shared.application.bus.command.CommandBus;
import com.letgo.shared.application.event.DomainEventSubscriber;
import com.letgo.shared.domain.DomainEvent;
import com.letgo.shared.infrastructure.InfrastructureService;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;

@Async
@InfrastructureService
final public class WhisperTitleOnBookCreated implements DomainEventSubscriber {
    private final CommandBus commandBus;

    public WhisperTitleOnBookCreated(CommandBus commandBus) {
        this.commandBus = commandBus;
    }

    @Override
    public boolean isSubscribedTo(DomainEvent event) {
        return BookCreated.class.equals(event.getClass());
    }

    @Override
    @EventListener(BookCreated.class)
    public void consume(DomainEvent event) throws Throwable {
        if (event instanceof BookCreated) {
            BookCreated bookCreated = (BookCreated) event;
            commandBus.dispatch(
                    new ChangeTitleCommand(
                            event.aggregateId(),
                            bookCreated.title().toLowerCase(),
                            bookCreated.titleCreatedAt().plusNanos(10000)
                    )
            );
        }
    }
}
