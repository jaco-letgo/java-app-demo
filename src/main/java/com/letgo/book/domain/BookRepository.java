package com.letgo.book.domain;

public interface BookRepository {
    public Book find(BookId id);
}
