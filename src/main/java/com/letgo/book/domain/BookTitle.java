package com.letgo.book.domain;

import java.time.LocalDateTime;
import java.util.Objects;

final public class BookTitle {
    private final String value;
    private final LocalDateTime createdAt;

    private BookTitle(String value, LocalDateTime createdAt) {
        this.value = value;
        this.createdAt = createdAt;
    }

    public static BookTitle create(String value) {
        return new BookTitle(value, LocalDateTime.now());
    }

    public static BookTitle create(String value, LocalDateTime createdAt) {
        return new BookTitle(value, createdAt);
    }

    public String value() {
        return value;
    }

    public LocalDateTime createdAt() {
        return createdAt;
    }

    @Override
    public boolean equals(Object anObject) {
        return anObject instanceof BookTitle
                && value().equals(((BookTitle) anObject).value())
                && createdAt().equals(((BookTitle) anObject).createdAt());
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, createdAt);
    }

    public boolean isNewerThan(BookTitle title) {
        return this.createdAt.isAfter(title.createdAt());
    }
}
