package com.letgo.book.domain;

import com.letgo.shared.domain.AggregateRoot;

final public class Book extends AggregateRoot {
    private final BookId id;
    private BookTitle title;

    private Book(BookId id, BookTitle title) {
        this.id = id;
        this.title = title;
    }

    public static Book create(BookId id, BookTitle title) {
        Book book = new Book(id, title);
        book.storeEvent(new BookCreated(id.value(), title.value()));
        return book;
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
