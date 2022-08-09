package com.letgo.shared.infrastructure.bus.command

import com.letgo.shared.application.bus.command.Command
import com.letgo.shared.infrastructure.InfrastructureService
import com.letgo.shared.infrastructure.bus.Consumer
import com.letgo.shared.infrastructure.bus.serialize.MessageSerializer

@InfrastructureService
class InMemoryAsyncCommandConsumer(
    private val serializer: MessageSerializer<Command>,
    private val handlerFinder: CommandHandlerFinder,
    private val consumer: SerializedCommandAsyncConsumer,
) : Consumer {
    override fun consume() {
        consumer.start { message ->
            val command = serializer.deserialize(message)
            handlerFinder.forCommand(command).handle(command)
        }
    }
}
