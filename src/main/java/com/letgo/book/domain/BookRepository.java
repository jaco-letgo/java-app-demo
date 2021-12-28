package com.letgo.book.domain;

import java.util.Optional;

public interface BookRepository {
    public Optional<Book> find(BookId id);
}
