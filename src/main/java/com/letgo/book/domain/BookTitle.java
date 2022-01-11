package com.letgo.book.domain;

final public class BookTitle {
    private final String value;

    private BookTitle(String value) {
        this.value = value;
    }

    public static BookTitle create() {
        return new BookTitle("");
    }

    public static BookTitle create(String value) {
        return new BookTitle(value);
    }

    public String value() {
        return value;
    }

    @Override
    public boolean equals(Object anObject) {
        return anObject instanceof BookTitle && value().equals(((BookTitle) anObject).value());
    }

    @Override
    public int hashCode() {
        return value().hashCode();
    }
}
