package com.letgo.book.domain;

import com.letgo.shared.domain.DomainEvent;

import java.util.Objects;

public class BookTitleChanged extends DomainEvent {
    private final String id;
    private final String oldTitle;
    private final String newTitle;

    public BookTitleChanged(String id, String oldTitle, String newTitle) {
        this.id = id;
        this.oldTitle = oldTitle;
        this.newTitle = newTitle;
    }

    public String id() {
        return id;
    }

    public String oldTitle() {
        return oldTitle;
    }

    public String newTitle() {
        return newTitle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookTitleChanged that = (BookTitleChanged) o;
        return Objects.equals(id, that.id)
                && Objects.equals(oldTitle, that.oldTitle)
                && Objects.equals(newTitle, that.newTitle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, oldTitle, newTitle);
    }
}
