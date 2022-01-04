package com.letgo.book.application.changeTitle;

import com.letgo.shared.application.bus.command.CommandHandler;
import com.letgo.book.domain.Book;
import com.letgo.book.domain.BookFinder;
import com.letgo.book.domain.BookId;
import com.letgo.book.domain.BookRepository;
import org.springframework.stereotype.Service;

@Service
public class ChangeTitleCommandHandler implements CommandHandler<ChangeTitleCommand> {
    private final BookRepository repository;
    private final BookFinder finder;

    public ChangeTitleCommandHandler(BookRepository repository, BookFinder finder) {
        this.repository = repository;
        this.finder = finder;
    }

    public void handle(ChangeTitleCommand command) {
        Book book = finder.find(BookId.create(command.id()));
        book.changeTitle(command.newTitle());
        repository.save(book);
    }
}
