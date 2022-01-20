package com.letgo.book.unit.application;

import com.letgo.book.domain.Book;
import com.letgo.book.domain.BookRepository;
import com.letgo.book.infrastructure.persistence.InMemoryBookRepository;
import com.letgo.book.unit.domain.BookMother;
import com.letgo.shared.application.event.DomainEventPublisher;
import com.letgo.shared.application.event.DomainEventSubscriber;
import com.letgo.shared.domain.DomainEvent;
import com.letgo.shared.infrastructure.event.publisher.InMemorySyncDomainEventPublisher;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

abstract public class BookTestCase {
    protected final BookRepository repository = new InMemoryBookRepository();
    protected final List<DomainEventSubscriber> subscribers = new ArrayList<>();
    protected final DomainEventPublisher publisher = new InMemorySyncDomainEventPublisher(subscribers);

    protected Book anExistingBook() {
        Book currentBook = BookMother.random();
        repository.save(currentBook);
        return currentBook;
    }

    protected void eventsShouldBePublished() {
        subscribers.forEach(subscriber -> assertTrue(subscriberHasBeenCalled(subscriber)));
    }

    protected void eventsShouldNotBePublished() {
        subscribers.forEach(subscriber -> assertFalse(subscriberHasBeenCalled(subscriber)));
    }

    protected void expectDomainEventsToBePublished(DomainEvent... events) {
        if (0 == events.length) {
            subscribers.add(new SpyDomainEventSubscriber());
        }
        for (DomainEvent event : events) {
            subscribers.add(new SpyDomainEventSubscriber(event));
        }
    }

    private static boolean subscriberHasBeenCalled(DomainEventSubscriber subscriber) {
        return subscriber instanceof SpyDomainEventSubscriber
                && ((SpyDomainEventSubscriber) subscriber).hasBeenCalled();
    }
}
