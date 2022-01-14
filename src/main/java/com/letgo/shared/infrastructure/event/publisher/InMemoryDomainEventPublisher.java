package com.letgo.shared.infrastructure.event.publisher;

import com.letgo.shared.application.event.DomainEventPublisher;
import com.letgo.shared.application.event.DomainEventSubscriber;
import com.letgo.shared.domain.DomainEvent;

import java.util.ArrayList;
import java.util.List;

final public class InMemoryDomainEventPublisher implements DomainEventPublisher {
    private final List<DomainEventSubscriber> subscribers;

    public InMemoryDomainEventPublisher() {
        this.subscribers = new ArrayList<>();
    }

    public void add(DomainEventSubscriber subscriber) {
        subscribers.add(subscriber);
    }

    @Override
    public void publish(List<DomainEvent> events) {
        events.forEach(event -> subscribers.forEach(subscriber -> tryToConsumeEvent(event, subscriber)));
    }

    private void tryToConsumeEvent(DomainEvent event, DomainEventSubscriber subscriber) {
        if (subscriber.isSubscribedTo(event)) {
            subscriber.consume(event);
        }
    }
}
