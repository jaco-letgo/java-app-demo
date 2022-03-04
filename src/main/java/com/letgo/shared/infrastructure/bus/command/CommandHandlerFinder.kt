package com.letgo.shared.infrastructure.bus.command

import com.letgo.shared.application.bus.command.Command
import com.letgo.shared.application.bus.command.CommandHandler
import com.letgo.shared.infrastructure.InfrastructureService
import kotlin.reflect.KClass

@InfrastructureService
class CommandHandlerFinder(handlers: List<CommandHandler<out Command>>) {
    private val handlers =
        handlers.associate { getCommandClass(it) to it as CommandHandler<Command> }

    fun forCommand(command: Command): CommandHandler<Command> =
        handlers[command::class] ?: throw RuntimeException("No handler found for ${command::class}")

    private fun getCommandClass(commandHandler: CommandHandler<out Command>) =
        commandHandler::class
            .supertypes.find { superClass -> superClass.classifier!!::class.isInstance(CommandHandler::class) }!!
            .arguments.find { argument -> argument.type!!.classifier!!::class.isInstance(Command::class) }!!
            .type!!
            .classifier as KClass<out Command>
}
