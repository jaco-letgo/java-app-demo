package com.letgo.shared.infrastructure.bus.command

import com.letgo.shared.application.bus.command.Command
import com.letgo.shared.application.bus.command.CommandBus

class InMemorySyncCommandBus(
    private val handlerFinder: CommandHandlerFinder
) : CommandBus {
    override fun dispatch(command: Command) = handlerFinder.forCommand(command).handle(command)
}
