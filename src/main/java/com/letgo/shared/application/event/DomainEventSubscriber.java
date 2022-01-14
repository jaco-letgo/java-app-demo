package com.letgo.shared.application.event;

import com.letgo.shared.domain.DomainEvent;

public interface DomainEventSubscriber {
    public boolean isSubscribedTo(DomainEvent event);
    public void consume(DomainEvent event);
}
