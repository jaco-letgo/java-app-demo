package com.letgo.book.domain;

final public class BookId {
    private final String value;

    private BookId(String value) {
        this.value = value;
    }

    public static BookId create(String value) {
        return new BookId(value);
    }

    public String value() {
        return value;
    }

    public boolean hasSameValueAs(BookId id) {
        return this.value.equals(id.value());
    }
}
