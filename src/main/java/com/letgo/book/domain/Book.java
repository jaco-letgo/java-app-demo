package com.letgo.book.domain;

final public class Book {
    private final BookId id;
    private final String title;

    private Book(BookId id, String title) {
        this.id = id;
        this.title = title;
    }

    public static Book create(BookId id, String title) {
        return new Book(id, title);
    }

    public BookId id() {
        return id;
    }

    public String title() {
        return title;
    }
}
