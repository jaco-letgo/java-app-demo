package com.letgo.book.unit.application.changeTitle;

import com.letgo.book.application.changeTitle.ChangeTitleCommand;
import com.letgo.book.application.changeTitle.ChangeTitleCommandHandler;
import com.letgo.book.domain.*;
import com.letgo.book.infrastructure.persistence.InMemoryBookRepository;
import com.letgo.book.unit.application.SpyDomainEventSubscriber;
import com.letgo.shared.application.event.DomainEventPublisher;
import com.letgo.shared.application.event.DomainEventSubscriber;
import com.letgo.shared.infrastructure.event.publisher.InMemoryDomainEventPublisher;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

final public class ChangeTitleCommandHandlerTest {
    private final BookRepository repository = new InMemoryBookRepository();
    private final List<DomainEventSubscriber> subscribers = new ArrayList<>();
    private final DomainEventPublisher publisher = new InMemoryDomainEventPublisher(subscribers);
    private final ChangeTitleCommandHandler handler = new ChangeTitleCommandHandler(
            repository,
            new BookFinder(repository),
            publisher
    );

    @Test
    public void itShouldChangeAnExistingBookTitle() {
        BookId id = BookId.create();
        BookTitle currentTitle = BookTitle.create("Whatever");
        Book currentBook = Book.create(id, currentTitle);
        currentBook.retrieveEvents();
        repository.save(currentBook);

        BookTitle newTitle = BookTitle.create("Title");

        BookTitleChanged expectedEvent = new BookTitleChanged(id.value(), currentTitle.value(), newTitle.value());
        SpyDomainEventSubscriber subscriber = new SpyDomainEventSubscriber(expectedEvent);
        subscribers.add(subscriber);

        handler.handle(new ChangeTitleCommand(id.value(), newTitle.value()));

        Optional<Book> optionalBook = repository.find(id);
        assertTrue(optionalBook.isPresent());

        Book book = optionalBook.get();
        assertEquals(id, book.id());
        assertEquals(newTitle, book.title());

        assertTrue(subscriber.hasBeenCalled());
    }

    @Test
    public void itShouldBeIdempotent() {
        BookId id = BookId.create();
        BookTitle title = BookTitle.create("Whatever");
        Book currentBook = Book.create(id, title);
        currentBook.retrieveEvents();
        repository.save(currentBook);

        SpyDomainEventSubscriber subscriber = new SpyDomainEventSubscriber();
        subscribers.add(subscriber);

        handler.handle(new ChangeTitleCommand(id.value(), title.value()));
        assertFalse(subscriber.hasBeenCalled());

        Optional<Book> optionalBook = repository.find(id);
        assertTrue(optionalBook.isPresent());

        Book book = optionalBook.get();
        assertEquals(currentBook, book);
    }
}
