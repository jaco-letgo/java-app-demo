package com.letgo.book.domain;

import com.letgo.shared.domain.DomainService;

import java.util.Optional;

@DomainService
final public class BookFinder {
    private final BookRepository repository;

    public BookFinder(BookRepository repository) {
        this.repository = repository;
    }

    public Book find(BookId id) {
        Optional<Book> book = repository.find(id);
        if (book.isEmpty()) {
            throw BookNotFound.withId(id);
        }
        return book.get();
    }
}
