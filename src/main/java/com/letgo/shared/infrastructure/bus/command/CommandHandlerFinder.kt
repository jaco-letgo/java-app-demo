package com.letgo.shared.infrastructure.bus.command

import com.letgo.shared.application.bus.command.Command
import com.letgo.shared.application.bus.command.CommandHandler
import com.letgo.shared.infrastructure.InfrastructureService

@InfrastructureService
class CommandHandlerFinder(handlers: List<CommandHandler<out Command>>) {
    private val handlers = handlers.associate { it.commandClass() to it as CommandHandler<Command> }

    fun forCommand(command: Command): CommandHandler<Command> =
        handlers[command::class] ?: throw NoSuchElementException("No handler found for ${command::class}")

    private fun CommandHandler<out Command>.commandClass() = this::class
        .supertypes.firstNotNullOf { supertype ->
            supertype.arguments.firstNotNullOf { argument ->
                argument.type?.classifier?.takeIf {
                    it::class.isInstance(Command::class)
                }
            }
        }
}
