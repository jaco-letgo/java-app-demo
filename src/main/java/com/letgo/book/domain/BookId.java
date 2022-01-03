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

    @Override
    public boolean equals(Object anObject) {
        return anObject instanceof BookId && this.value.equals(((BookId) anObject).value());
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
