package com.letgo.book.application

import com.letgo.book.domain.ABook
import com.letgo.book.domain.Book
import com.letgo.book.domain.BookRepository
import com.letgo.book.infrastructure.persistence.InMemoryBookRepository
import com.letgo.book.infrastructure.persistence.strategy.BookSpecificationStrategies
import com.letgo.shared.application.bus.event.DomainEventPublisher
import com.letgo.shared.application.bus.event.DomainEventSubscriber
import com.letgo.shared.domain.DomainEvent
import com.letgo.shared.infrastructure.bus.event.InMemorySyncDomainEventPublisher
import com.letgo.shared.infrastructure.bus.event.SpyDomainEventSubscriber
import com.letgo.shared.infrastructure.persistance.specification.SpecificationBuilder
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue

abstract class BookTestCase {
    protected val repository: BookRepository = InMemoryBookRepository(SpecificationBuilder(BookSpecificationStrategies))
    private val subscribers: MutableList<DomainEventSubscriber> = mutableListOf()
    protected val publisher: DomainEventPublisher = InMemorySyncDomainEventPublisher(subscribers)

    protected fun anExistingBook(): Book = ABook.random().also { repository.save(it) }

    protected fun eventsShouldBePublished() = subscribers.forEach { assertTrue(subscriberHasBeenCalled(it)) }

    protected fun eventsShouldNotBePublished() = subscribers.forEach { assertFalse(subscriberHasBeenCalled(it)) }

    protected fun expectDomainEventsToBePublished(vararg events: DomainEvent) {
        if (events.isEmpty()) subscribers.add(SpyDomainEventSubscriber())
        else events.forEach { subscribers.add(SpyDomainEventSubscriber(it)) }
    }

    private fun subscriberHasBeenCalled(subscriber: DomainEventSubscriber): Boolean =
        subscriber is SpyDomainEventSubscriber && subscriber.hasBeenCalled()
}
