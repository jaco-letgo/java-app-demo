package com.letgo.book.application.changeTitle;

import com.letgo.book.domain.*;
import com.letgo.shared.application.bus.command.CommandHandler;

final public class ChangeTitleCommandHandler implements CommandHandler<ChangeTitleCommand> {
    private final BookRepository repository;
    private final BookFinder finder;

    public ChangeTitleCommandHandler(BookRepository repository, BookFinder finder) {
        this.repository = repository;
        this.finder = finder;
    }

    public void handle(ChangeTitleCommand command) {
        Book book = finder.find(BookId.create(command.id()));
        book.changeTitle(BookTitle.create(command.newTitle()));
        repository.save(book);
    }
}
