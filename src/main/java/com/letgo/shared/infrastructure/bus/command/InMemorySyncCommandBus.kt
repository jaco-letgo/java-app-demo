package com.letgo.shared.infrastructure.bus.command

import com.letgo.shared.application.bus.command.Command
import com.letgo.shared.application.bus.command.CommandBus
import com.letgo.shared.application.bus.command.CommandHandler
import com.letgo.shared.infrastructure.InfrastructureService
import java.util.function.Consumer
import kotlin.reflect.KClass

@InfrastructureService
class InMemorySyncCommandBus(handlers: List<CommandHandler<out Command>>) : CommandBus {
    private val handlers: MutableMap<KClass<out Command>, CommandHandler<Command>> = mutableMapOf()

    init {
        handlers.forEach(Consumer { commandHandler: CommandHandler<out Command> ->
            this.handlers[getCommandClass(commandHandler)] = commandHandler as CommandHandler<Command>
        })
    }

    @Throws(Exception::class)
    override fun dispatch(command: Command) {
        synchronized(this) {
            handlers[command::class]?.handle(command) ?: throw Exception("No handler found for ${command::class}")
        }
    }

    private fun getCommandClass(commandHandler: CommandHandler<out Command>) =
        commandHandler::class
            .supertypes.find { superClass -> superClass.classifier!!::class.isInstance(CommandHandler::class) }!!
            .arguments.find { argument -> argument.type!!.classifier!!::class.isInstance(Command::class) }!!
            .type!!
            .classifier as KClass<out Command>
}
