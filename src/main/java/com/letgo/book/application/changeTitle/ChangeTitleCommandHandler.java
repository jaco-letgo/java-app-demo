package com.letgo.book.application.changeTitle;

import com.letgo.book.domain.*;
import com.letgo.shared.application.bus.command.CommandHandler;
import com.letgo.shared.application.event.DomainEventPublisher;

final public class ChangeTitleCommandHandler implements CommandHandler<ChangeTitleCommand> {
    private final BookRepository repository;
    private final BookFinder finder;
    private final DomainEventPublisher publisher;

    public ChangeTitleCommandHandler(BookRepository repository, BookFinder finder, DomainEventPublisher publisher) {
        this.repository = repository;
        this.finder = finder;
        this.publisher = publisher;
    }

    public void handle(ChangeTitleCommand command) {
        Book book = finder.find(BookId.create(command.id()));
        BookTitle newTitle = BookTitle.create(command.newTitle());
        if (!book.title().equals(newTitle)) {
            book.changeTitle(newTitle);
            repository.save(book);
            publisher.publish(book.retrieveEvents());
        }
    }
}
