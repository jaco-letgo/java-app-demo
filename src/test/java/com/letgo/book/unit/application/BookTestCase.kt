package com.letgo.book.unit.application

import com.letgo.book.domain.Book
import com.letgo.book.domain.BookRepository
import com.letgo.book.infrastructure.persistence.InMemoryBookRepository
import com.letgo.book.unit.domain.BookMother
import com.letgo.shared.application.event.DomainEventPublisher
import com.letgo.shared.application.event.DomainEventSubscriber
import com.letgo.shared.domain.DomainEvent
import com.letgo.shared.infrastructure.event.publisher.InMemorySyncDomainEventPublisher
import com.letgo.shared.infrastructure.event.publisher.SpyDomainEventSubscriber
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue

abstract class BookTestCase {
    protected val repository: BookRepository = InMemoryBookRepository()
    private val subscribers: MutableList<DomainEventSubscriber> = mutableListOf()
    protected val publisher: DomainEventPublisher = InMemorySyncDomainEventPublisher(subscribers)

    protected fun anExistingBook(): Book = BookMother.random().also { repository.save(it) }

    protected fun eventsShouldBePublished() = subscribers.forEach { assertTrue(subscriberHasBeenCalled(it)) }

    protected fun eventsShouldNotBePublished() = subscribers.forEach { assertFalse(subscriberHasBeenCalled(it)) }

    protected fun expectDomainEventsToBePublished(vararg events: DomainEvent) {
        if (events.isEmpty()) subscribers.add(SpyDomainEventSubscriber())
        else events.forEach { subscribers.add(SpyDomainEventSubscriber(it)) }
    }

    private fun subscriberHasBeenCalled(subscriber: DomainEventSubscriber): Boolean =
        subscriber is SpyDomainEventSubscriber && subscriber.hasBeenCalled()
}
