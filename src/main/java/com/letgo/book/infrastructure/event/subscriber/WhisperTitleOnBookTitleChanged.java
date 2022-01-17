package com.letgo.book.infrastructure.event.subscriber;

import com.letgo.book.domain.BookTitleChanged;
import com.letgo.shared.application.event.DomainEventSubscriber;
import com.letgo.shared.domain.DomainEvent;
import com.letgo.shared.infrastructure.InfrastructureService;
import org.springframework.context.event.EventListener;

@InfrastructureService
final public class WhisperTitleOnBookTitleChanged implements DomainEventSubscriber {
    @Override
    public boolean isSubscribedTo(DomainEvent event) {
        return BookTitleChanged.class.equals(event.getClass());
    }

    @Override
    @EventListener(BookTitleChanged.class)
    public void consume(DomainEvent event) {
        if (event instanceof BookTitleChanged) {
            System.out.println(((BookTitleChanged) event).newTitle().toLowerCase());
        }
    }
}
