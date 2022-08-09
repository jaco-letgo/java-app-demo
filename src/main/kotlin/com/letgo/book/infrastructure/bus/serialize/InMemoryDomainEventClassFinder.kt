package com.letgo.book.infrastructure.bus.serialize

import com.letgo.shared.domain.DomainEvent
import com.letgo.shared.infrastructure.InfrastructureService
import com.letgo.shared.infrastructure.bus.serialize.DomainEventClassFinder
import org.reflections.Reflections
import kotlin.reflect.KClass

@InfrastructureService
object InMemoryDomainEventClassFinder : DomainEventClassFinder {
    private var mapping: Map<String, Class<out DomainEvent>> =
        Reflections("com.letgo.book").getSubTypesOf(DomainEvent::class.java).associateBy { it.simpleName.toString() }

    override fun find(type: String): KClass<out DomainEvent> =
        mapping[type]?.kotlin ?: throw NoSuchElementException("No mapping for domain event with type $type")
}
