package com.letgo.book.infrastructure.event.subscriber;

import com.letgo.book.domain.BookCreated;
import com.letgo.shared.application.event.DomainEventSubscriber;
import com.letgo.shared.domain.DomainEvent;
import com.letgo.shared.infrastructure.InfrastructureService;
import org.springframework.context.event.EventListener;

@InfrastructureService
final public class ShoutTitleOnBookCreated implements DomainEventSubscriber {
    @Override
    public boolean isSubscribedTo(DomainEvent event) {
        return BookCreated.class.equals(event.getClass());
    }

    @Override
    @EventListener(BookCreated.class)
    public void consume(DomainEvent event) {
        if (event instanceof BookCreated) {
            System.out.println(((BookCreated) event).title().toUpperCase());
        }
    }
}
