package com.letgo.book.infrastructure.bus.serialize

import com.letgo.book.application.changeTitle.ChangeTitleCommand
import com.letgo.book.application.create.CreateBookCommand
import com.letgo.shared.application.bus.command.Command
import com.letgo.shared.infrastructure.InfrastructureService
import com.letgo.shared.infrastructure.bus.serialize.CommandClassFinder
import kotlin.reflect.KClass

@InfrastructureService
object InMemoryCommandClassFinder : CommandClassFinder {
    private val mapping: Map<String, KClass<out Command>> = listOf(
        CreateBookCommand::class,
        ChangeTitleCommand::class,
    ).associateBy { it.simpleName.toString() }

    override fun find(type: String): KClass<out Command> =
        mapping[type] ?: throw NoSuchElementException("No mapping for command with type $type")
}
