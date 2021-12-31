package com.letgo.book.domain;

final public class BookNotFound extends IndexOutOfBoundsException {
    private BookNotFound(String message) {
        super(message);
    }

    public static BookNotFound withId(BookId id) {
        return new BookNotFound("Book not found with id " + id.value());
    }
}
