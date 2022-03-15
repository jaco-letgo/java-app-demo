package com.letgo.book.infrastructure.bus.serialize

import com.letgo.shared.application.bus.command.Command
import com.letgo.shared.infrastructure.InfrastructureService
import com.letgo.shared.infrastructure.bus.serialize.CommandClassFinder
import org.reflections.Reflections
import kotlin.reflect.KClass

@InfrastructureService
object InMemoryCommandClassFinder : CommandClassFinder {
    private val mapping: Map<String, Class<out Command>> =
        Reflections("com.letgo.book").getSubTypesOf(Command::class.java).associateBy { it.simpleName.toString() }

    override fun find(type: String): KClass<out Command> =
        mapping[type]?.kotlin ?: throw NoSuchElementException("No mapping for command with type $type")
}
