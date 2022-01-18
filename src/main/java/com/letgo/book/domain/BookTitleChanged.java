package com.letgo.book.domain;

import com.letgo.shared.domain.DomainEvent;

public class BookTitleChanged extends DomainEvent {
    private final String oldTitle;
    private final String newTitle;

    public BookTitleChanged(String id, String oldTitle, String newTitle) {
        super(id);
        this.oldTitle = oldTitle;
        this.newTitle = newTitle;
    }

    public String oldTitle() {
        return oldTitle;
    }

    public String newTitle() {
        return newTitle;
    }

    @Override
    public String name() {
        return "BookTitleChanged";
    }

    @Override
    public String body() {
        return "{'oldTitle': " + oldTitle + ", 'newTitle': " + newTitle + "}";
    }
}
