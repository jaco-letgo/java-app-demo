package com.letgo.book.unit.application;

import com.letgo.shared.application.event.DomainEventSubscriber;
import com.letgo.shared.domain.DomainEvent;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SpyDomainEventSubscriber implements DomainEventSubscriber {
    private final DomainEvent expectedEvent;

    public SpyDomainEventSubscriber(DomainEvent expectedEvent) {
        this.expectedEvent = expectedEvent;
    }

    @Override
    public boolean isSubscribedTo(DomainEvent event) {
        return true;
    }

    @Override
    public void consume(DomainEvent event) {
        assertEquals(expectedEvent, event);
    }
}
