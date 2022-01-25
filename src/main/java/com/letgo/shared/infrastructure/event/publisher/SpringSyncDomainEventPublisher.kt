package com.letgo.shared.infrastructure.event.publisher;

import com.letgo.shared.application.event.DomainEventPublisher;
import com.letgo.shared.domain.DomainEvent;
import org.springframework.context.ApplicationEventPublisher;

import java.util.List;

final public class SpringSyncDomainEventPublisher implements DomainEventPublisher {
    private final ApplicationEventPublisher publisher;

    public SpringSyncDomainEventPublisher(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    @Override
    public void publish(List<DomainEvent> events) {
        try {
            events.forEach(publisher::publishEvent);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }
}
