package com.letgo.shared.infrastructure.bus.command

import com.letgo.shared.application.bus.command.Command
import com.letgo.shared.application.bus.command.CommandBus
import com.letgo.shared.application.bus.command.CommandHandler
import com.letgo.shared.infrastructure.InfrastructureService
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import java.util.function.Consumer

@InfrastructureService
class InMemorySyncCommandBus(handlers: List<CommandHandler<out Command>>) : CommandBus {
    private val handlers: MutableMap<Class<out Command>, CommandHandler<out Command>> = HashMap()

    init {
        handlers.forEach(Consumer { commandHandler: CommandHandler<out Command> ->
            this.handlers[getCommandClass(
                commandHandler
            )] = commandHandler
        })
    }

    @Throws(Exception::class)
    override fun dispatch(command: Command) {
        if (!handlers.containsKey(command.javaClass)) {
            throw Exception(String.format("No handler found for %s", command.javaClass.name))
        }
        val commandHandler = handlers[command.javaClass] as CommandHandler<Command>
        synchronized(this) { commandHandler.handle(command) }
    }

    private fun getCommandClass(handler: CommandHandler<out Command>): Class<out Command> {
        return actualTypeArgument(handler) as Class<out Command>
    }

    private fun actualTypeArgument(handler: CommandHandler<out Command>): Type {
        return (handler.javaClass.genericInterfaces[0] as ParameterizedType).actualTypeArguments[0]
    }
}
