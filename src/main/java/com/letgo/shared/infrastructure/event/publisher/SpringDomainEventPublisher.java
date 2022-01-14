package com.letgo.shared.infrastructure.event.publisher;

import com.letgo.shared.application.event.DomainEventPublisher;
import com.letgo.shared.domain.DomainEvent;
import com.letgo.shared.infrastructure.InfrastructureService;
import org.springframework.context.ApplicationEventPublisher;

import java.util.List;

@InfrastructureService
final public class SpringDomainEventPublisher implements DomainEventPublisher {
    private final ApplicationEventPublisher publisher;

    public SpringDomainEventPublisher(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    @Override
    public void publish(List<DomainEvent> events) {
        events.forEach(publisher::publishEvent);
    }
}
