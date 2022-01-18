package com.letgo.book.unit.domain;

import com.letgo.book.domain.Book;
import com.letgo.book.domain.BookId;
import com.letgo.book.domain.BookTitle;

final public class BookMother {
    public static Book create(BookId id, BookTitle title) {
        Book book = Book.create(id, title);
        book.retrieveEvents();
        return book;
    }

    public static Book random() {
        return BookMother.create(BookIdMother.random(), BookTitleMother.random());
    }
}
