package com.letgo.book.unit.application.changeTitle;

import com.letgo.book.application.changeTitle.ChangeTitleCommand;
import com.letgo.book.application.changeTitle.ChangeTitleCommandHandler;
import com.letgo.book.domain.Book;
import com.letgo.book.domain.BookFinder;
import com.letgo.book.domain.BookId;
import com.letgo.book.domain.BookRepository;
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
        BookId id = BookId.create("123");
        String currentTitle = "Whatever";
        Book currentBook = Book.create(id, currentTitle);
        repository.save(currentBook);

        String newTitle = "Title";

        handler.handle(new ChangeTitleCommand(id.value(), newTitle));

        Optional<Book> optionalBook = repository.find(id);
        assertTrue(optionalBook.isPresent());

        Book book = optionalBook.get();
        assertTrue(book.id().hasSameValueAs(id));
        assertEquals(newTitle, book.title());
    }
}
