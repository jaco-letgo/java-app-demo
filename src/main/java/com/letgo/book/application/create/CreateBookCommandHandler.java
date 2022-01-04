package com.letgo.book.application.create;

import com.letgo.shared.application.bus.command.CommandHandler;
import com.letgo.book.domain.Book;
import com.letgo.book.domain.BookId;
import com.letgo.book.domain.BookRepository;
import org.springframework.stereotype.Service;

@Service
public class CreateBookCommandHandler implements CommandHandler<CreateBookCommand> {
    private final BookRepository repository;

    public CreateBookCommandHandler(BookRepository repository) {
        this.repository = repository;
    }

    @Override
    public void handle(CreateBookCommand command) {
        Book book = Book.create(BookId.create(command.id()), command.title());
        repository.save(book);
    }
}
