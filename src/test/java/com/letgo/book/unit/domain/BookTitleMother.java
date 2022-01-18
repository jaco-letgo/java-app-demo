package com.letgo.book.unit.domain;

import com.letgo.book.domain.BookTitle;

final public class BookTitleMother {
    public static BookTitle create(String title) {
        return BookTitle.create(title);
    }

    public static BookTitle random() {
        return BookTitleMother.create("random");
    }
}
