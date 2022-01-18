package com.letgo.book.domain;

import com.letgo.shared.domain.DomainEvent;

final public class BookCreated extends DomainEvent {
    private final String title;

    public BookCreated(String id, String title) {
        super(id);
        this.title = title;
    }

    public String title() {
        return title;
    }

    @Override
    public String name() {
        return "BookCreated";
    }

    @Override
    public String body() {
        return "{'title': " + title + "}";
    }
}
