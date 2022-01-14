package com.letgo.shared.application.event;

import com.letgo.shared.domain.DomainEvent;

import java.util.List;

public interface DomainEventPublisher {
    public void publish(List<DomainEvent> events);
}
