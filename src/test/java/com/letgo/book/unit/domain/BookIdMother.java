package com.letgo.book.unit.domain;

import com.letgo.book.domain.BookId;

final public class BookIdMother {
    public static BookId create(String id) {
        return BookId.create(id);
    }

    public static BookId random() {
        return BookId.create();
    }
}
