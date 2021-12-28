package com.letgo.book.application.create;

import com.letgo.book.domain.Book;
import com.letgo.book.domain.BookId;
import com.letgo.book.domain.BookRepository;

public class CreateBookCommandHandler {
    private final BookRepository repository;

    public CreateBookCommandHandler(BookRepository repository) {
        this.repository = repository;
    }

    public void handle(CreateBookCommand command) {
        Book book = new Book(new BookId(command.id()), command.title());
        repository.save(book);
    }
}
