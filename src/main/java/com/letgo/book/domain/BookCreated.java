package com.letgo.book.domain;

import com.letgo.shared.domain.DomainEvent;

import java.time.LocalDateTime;

final public class BookCreated extends DomainEvent {
    private final String title;
    private final LocalDateTime titleCreatedAt;

    public BookCreated(String id, String title, LocalDateTime titleCreatedAt) {
        super(id);
        this.title = title;
        this.titleCreatedAt = titleCreatedAt;
    }

    public String title() {
        return title;
    }

    public LocalDateTime titleCreatedAt() {
        return titleCreatedAt;
    }

    @Override
    public String name() {
        return "BookCreated";
    }

    @Override
    public String body() {
        return "{'title': " + title + ", 'titleCreatedAt': " + titleCreatedAt.toString() + "}";
    }
}
