package com.letgo.book.unit.application.changeTitle;

import com.letgo.book.application.changeTitle.ChangeTitleCommand;
import com.letgo.book.application.changeTitle.ChangeTitleCommandHandler;
import com.letgo.book.domain.*;
import com.letgo.book.infrastructure.persistence.InMemoryBookRepository;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class ChangeTitleCommandHandlerTest {
    private final BookRepository repository = new InMemoryBookRepository();
    private final ChangeTitleCommandHandler handler = new ChangeTitleCommandHandler(
            repository,
            new BookFinder(repository)
    );

    @Test
    public void itShouldCreateABook() {
        BookId id = BookId.create();
        BookTitle currentTitle = BookTitle.create("Whatever");
        Book currentBook = Book.create(id, currentTitle);
        repository.save(currentBook);

        BookTitle newTitle = BookTitle.create("Title");

        handler.handle(new ChangeTitleCommand(id.value(), newTitle.value()));

        Optional<Book> optionalBook = repository.find(id);
        assertTrue(optionalBook.isPresent());

        Book book = optionalBook.get();
        assertEquals(id, book.id());
        assertEquals(newTitle, book.title());
    }
}
