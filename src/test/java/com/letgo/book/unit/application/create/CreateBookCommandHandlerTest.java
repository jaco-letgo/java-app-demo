package com.letgo.book.unit.application.create;

import com.letgo.book.application.create.CreateBookCommand;
import com.letgo.book.application.create.CreateBookCommandHandler;
import com.letgo.book.domain.Book;
import com.letgo.book.domain.BookId;
import com.letgo.book.domain.BookRepository;
import com.letgo.book.domain.BookTitle;
import com.letgo.book.infrastructure.persistence.InMemoryBookRepository;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

final public class CreateBookCommandHandlerTest {
    private final BookRepository repository = new InMemoryBookRepository();
    private final CreateBookCommandHandler handler = new CreateBookCommandHandler(repository);

    @Test
    public void itShouldCreateABook() {
        BookId id = BookId.create();
        BookTitle title = BookTitle.create("Title");

        handler.handle(new CreateBookCommand(id.value(), title.value()));

        Optional<Book> optionalBook = repository.find(id);
        assertTrue(optionalBook.isPresent());

        Book book = optionalBook.get();
        assertEquals(id, book.id());
        assertEquals(title, book.title());
    }
}
