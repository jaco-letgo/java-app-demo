package com.letgo.shared.infrastructure.bus.command

import com.letgo.shared.application.bus.command.Command
import com.letgo.shared.application.bus.command.CommandBus
import com.letgo.shared.infrastructure.InfrastructureService

@InfrastructureService
class InMemorySyncCommandBus(
    private val handlerFinder: CommandHandlerFinder
) : CommandBus {
    @Throws(RuntimeException::class)
    override fun dispatch(command: Command) {
        val handler = handlerFinder.forCommand(command)
        synchronized(this) {
            handler.handle(command)
        }
    }
}
