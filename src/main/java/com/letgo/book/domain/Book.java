package com.letgo.book.domain;

import com.letgo.shared.domain.AggregateRoot;

final public class Book extends AggregateRoot {
    private final BookId id;
    private BookTitle title;

    private Book(BookId id, BookTitle title) {
        this.id = id;
        this.title = title;
        storeEvent(new BookCreated(id.value(), title.value()));
    }

    public static Book create(String id, String title) {
        return new Book(BookId.create(id), BookTitle.create(title));
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
        storeEvent(new BookTitleChanged(id.value(), title.value(), newTitle.value()));
        title = newTitle;
    }
}
