package com.letgo.shared.domain;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public abstract class DomainEvent {
    private final UUID id;
    private final String aggregateId;
    private final LocalDateTime occurredOn;

    public DomainEvent(String aggregateId) {
        this.id = UUID.randomUUID();
        this.aggregateId = aggregateId;
        this.occurredOn = LocalDateTime.now();
    }

    public DomainEvent(String aggregateId, UUID id, LocalDateTime occurredOn) {
        this.id = id;
        this.aggregateId = aggregateId;
        this.occurredOn = occurredOn;
    }

    public final String id() {
        return id.toString();
    }

    public abstract String name();

    public final String aggregateId() {
        return aggregateId;
    }

    public final LocalDateTime occurredOn() {
        return occurredOn;
    }

    public abstract String body();

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DomainEvent that = (DomainEvent) o;
        return Objects.equals(id, that.id)
                && Objects.equals(name(), that.name())
                && Objects.equals(aggregateId, that.aggregateId)
                && Objects.equals(occurredOn, that.occurredOn)
                && Objects.equals(body(), that.body());
    }

    @Override
    public final int hashCode() {
        return Objects.hash(id, name(), aggregateId, occurredOn, body());
    }
}
