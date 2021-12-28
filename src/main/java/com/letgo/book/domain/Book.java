package com.letgo.book.domain;

public class Book {
    private final BookId id;
    private final String title;

    public Book(BookId id, String title) {
        this.id = id;
        this.title = title;
    }

    public BookId id() {
        return id;
    }

    public String title() {
        return title;
    }
}
