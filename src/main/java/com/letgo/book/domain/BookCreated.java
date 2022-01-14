package com.letgo.book.domain;

import com.letgo.shared.domain.DomainEvent;

import java.util.Objects;

final public class BookCreated extends DomainEvent {
    private final String id;
    private final String title;

    public BookCreated(String id, String title) {
        this.id = id;
        this.title = title;
    }

    public String id() {
        return id;
    }

    public String title() {
        return title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookCreated that = (BookCreated) o;
        return Objects.equals(id, that.id) && Objects.equals(title, that.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title);
    }
}
