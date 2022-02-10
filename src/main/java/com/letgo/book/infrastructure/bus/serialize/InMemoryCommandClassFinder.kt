package com.letgo.book.infrastructure.bus.serialize

import com.letgo.book.application.changeTitle.ChangeTitleCommand
import com.letgo.book.application.create.CreateBookCommand
import com.letgo.shared.application.bus.command.Command
import com.letgo.shared.infrastructure.InfrastructureService
import com.letgo.shared.infrastructure.bus.serialize.CommandClassFinder
import kotlin.reflect.KClass

@InfrastructureService
object InMemoryCommandClassFinder : CommandClassFinder {
    private val mapping: Map<String, KClass<out Command>> = mapOf(
        Pair("CreateBookCommand", CreateBookCommand::class),
        Pair("ChangeTitleCommand", ChangeTitleCommand::class)
    )
    override fun find(type: String): KClass<out Command> =
        mapping[type] ?: throw RuntimeException("No mapping for command with type $type")
}
