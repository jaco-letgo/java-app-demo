package com.letgo.shared.infrastructure.bus.serialize

import com.letgo.shared.domain.DomainEvent
import kotlin.reflect.KClass

interface DomainEventClassFinder {
    fun find(type: String): KClass<out DomainEvent>
}
