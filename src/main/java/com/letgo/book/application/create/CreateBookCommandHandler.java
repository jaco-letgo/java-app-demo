package com.letgo.book.application.create;

import com.letgo.book.domain.Book;
import com.letgo.book.domain.BookId;
import com.letgo.book.domain.BookRepository;
import com.letgo.book.domain.BookTitle;
import com.letgo.shared.application.bus.command.CommandHandler;
import com.letgo.shared.application.event.DomainEventPublisher;

final public class CreateBookCommandHandler implements CommandHandler<CreateBookCommand> {
    private final BookRepository repository;
    private final DomainEventPublisher publisher;

    public CreateBookCommandHandler(BookRepository repository, DomainEventPublisher publisher) {
        this.repository = repository;
        this.publisher = publisher;
    }

    @Override
    public void handle(CreateBookCommand command) {
        Book book = Book.create(BookId.create(command.id()), BookTitle.create(command.title()));
        repository.save(book);
        publisher.publish(book.retrieveEvents());
    }
}
