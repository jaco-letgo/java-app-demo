package com.letgo.book.domain;

final public class Book {
    private final BookId id;
    private BookTitle title;

    private Book(BookId id, BookTitle title) {
        this.id = id;
        this.title = title;
    }

    public static Book create(BookId id, BookTitle title) {
        return new Book(id, title);
    }

    public BookId id() {
        return id;
    }

    public BookTitle title() {
        return title;
    }

    public void changeTitle(BookTitle newTitle) {
        title = newTitle;
    }
}
