package com.letgo.book.unit.application.changeTitle;

import com.letgo.book.application.changeTitle.ChangeTitleCommand;
import com.letgo.book.application.changeTitle.ChangeTitleCommandHandler;
import com.letgo.book.domain.*;
import com.letgo.book.unit.application.BookTestCase;
import com.letgo.book.unit.domain.BookTitleMother;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

final public class ChangeTitleCommandHandlerTest extends BookTestCase {
    private final ChangeTitleCommandHandler handler = new ChangeTitleCommandHandler(
            repository,
            new BookFinder(repository),
            publisher
    );

    @Test
    public void itShouldChangeAnExistingBookTitle() {
        Book currentBook = anExistingBook();
        BookId id = currentBook.id();

        BookTitle newTitle = BookTitleMother.random();

        expectDomainEventsToBePublished(new BookTitleChanged(
                id.value(),
                currentBook.title().value(),
                newTitle.value(),
                newTitle.createdAt()
        ));

        handler.handle(new ChangeTitleCommand(id.value(), newTitle.value(), newTitle.createdAt().toString()));

        Book book = repository.find(id).orElseThrow();
        assertEquals(id, book.id());
        assertEquals(newTitle, book.title());

        eventsShouldBePublished();
    }

    @Test
    public void itShouldBeIdempotent() {
        Book currentBook = anExistingBook();
        BookId id = currentBook.id();
        BookTitle title = currentBook.title();

        expectDomainEventsToBePublished();

        handler.handle(new ChangeTitleCommand(id.value(), title.value(), title.createdAt().toString()));

        assertEquals(currentBook, repository.find(id).orElseThrow());
        eventsShouldNotBePublished();
    }
}
