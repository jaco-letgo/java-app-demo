package com.letgo.book.unit.domain;

import com.letgo.book.domain.BookTitle;

import java.time.LocalDateTime;

final public class BookTitleMother {
    public static BookTitle create(String title) {
        return BookTitle.create(title, LocalDateTime.now());
    }

    public static BookTitle create(String title, LocalDateTime createdAt) {
        return BookTitle.create(title, createdAt);
    }

    public static BookTitle random() {
        return BookTitleMother.create("random");
    }
}
