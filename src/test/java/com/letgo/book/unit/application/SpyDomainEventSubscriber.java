package com.letgo.book.unit.application;

import com.letgo.shared.application.event.DomainEventSubscriber;
import com.letgo.shared.domain.DomainEvent;

import static org.junit.jupiter.api.Assertions.assertEquals;

final public class SpyDomainEventSubscriber implements DomainEventSubscriber {
    private boolean hasBeenCalled = false;
    private final DomainEvent expectedEvent;

    public SpyDomainEventSubscriber() {
        this.expectedEvent = null;
    }

    public SpyDomainEventSubscriber(DomainEvent expectedEvent) {
        this.expectedEvent = expectedEvent;
    }

    @Override
    public boolean isSubscribedTo(DomainEvent event) {
        return true;
    }

    @Override
    public void consume(DomainEvent event) {
        hasBeenCalled = true;
        assertEquals(expectedEvent, event);
    }

    public boolean hasBeenCalled() {
        return hasBeenCalled;
    }
}
