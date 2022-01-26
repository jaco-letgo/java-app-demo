package com.letgo.book.unit.application;

import com.letgo.shared.application.event.DomainEventSubscriber;
import com.letgo.shared.domain.DomainEvent;

import static org.junit.jupiter.api.Assertions.*;

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
        assertNotNull(expectedEvent);
        assertEquals(expectedEvent.aggregateId(), event.aggregateId());
        assertEquals(expectedEvent.name(), event.name());
        assertEquals(expectedEvent.body(), event.body());
        assertEquals(expectedEvent.occurredOn(), event.occurredOn());
    }

    public boolean hasBeenCalled() {
        return hasBeenCalled;
    }
}
