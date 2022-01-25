package com.letgo.shared.domain;

import java.util.ArrayList;
import java.util.List;

public abstract class AggregateRoot {
    private List<DomainEvent> events = new ArrayList<>();

    public List<DomainEvent> retrieveEvents() {
        List<DomainEvent> events = this.events;
        this.events = new ArrayList<>();
        return events;
    }

    protected void storeEvent(DomainEvent event) {
        this.events.add(event);
    }
}
