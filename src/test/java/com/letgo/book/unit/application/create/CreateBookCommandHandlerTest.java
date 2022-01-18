package com.letgo.book.unit.application.create;

import com.letgo.book.application.create.CreateBookCommand;
import com.letgo.book.application.create.CreateBookCommandHandler;
import com.letgo.book.domain.Book;
import com.letgo.book.domain.BookCreated;
import com.letgo.book.domain.BookId;
import com.letgo.book.domain.BookTitle;
import com.letgo.book.unit.application.BookTestCase;
import com.letgo.book.unit.domain.BookIdMother;
import com.letgo.book.unit.domain.BookTitleMother;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

final public class CreateBookCommandHandlerTest extends BookTestCase {
    private final CreateBookCommandHandler handler = new CreateBookCommandHandler(repository, publisher);

    @Test
    public void itShouldCreateABook() {
        BookId id = BookIdMother.random();
        BookTitle title = BookTitleMother.create("Title");

        expectDomainEventsToBePublished(new BookCreated(id.value(), title.value()));

        handler.handle(new CreateBookCommand(id.value(), title.value()));

        Book book = repository.find(id).orElseThrow();
        assertEquals(id, book.id());
        assertEquals(title, book.title());

        eventsShouldBePublished();
    }

    @Test
    public void itShouldBeIdempotent() {
        Book currentBook = anExistingBook();
        BookId id = currentBook.id();

        expectDomainEventsToBePublished();

        handler.handle(new CreateBookCommand(id.value(), currentBook.title().value()));

        assertEquals(currentBook, repository.find(id).orElseThrow());

        eventsShouldNotBePublished();
    }
}
