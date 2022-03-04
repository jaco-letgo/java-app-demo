package com.letgo.book.infrastructure.bus.serialize

import com.letgo.book.domain.BookCreated
import com.letgo.book.domain.BookTitleChanged
import com.letgo.shared.domain.DomainEvent
import com.letgo.shared.infrastructure.InfrastructureService
import com.letgo.shared.infrastructure.bus.serialize.DomainEventClassFinder
import kotlin.reflect.KClass

@InfrastructureService
object InMemoryDomainEventClassFinder : DomainEventClassFinder {
    private val mapping: Map<String, KClass<out DomainEvent>> = listOf(
        BookCreated::class,
        BookTitleChanged::class,
    ).associateBy { it.simpleName.toString() }

    override fun find(type: String): KClass<out DomainEvent> =
        mapping[type] ?: throw NoSuchElementException("No mapping for domain event with type $type")
}
