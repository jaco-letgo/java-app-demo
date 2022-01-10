package com.letgo.book.domain;

import java.util.UUID;

final public class BookId {
    private final UUID value;

    private BookId(UUID value) {
        this.value = value;
    }

    public static BookId create() {
        return new BookId(UUID.randomUUID());
    }

    public static BookId create(String value) {
        return new BookId(UUID.fromString(value));
    }

    public String value() {
        return value.toString();
    }

    @Override
    public boolean equals(Object anObject) {
        return anObject instanceof BookId && value().equals(((BookId) anObject).value());
    }

    @Override
    public int hashCode() {
        return value().hashCode();
    }
}
