package com.letgo.shared.infrastructure.bus.command

import com.letgo.shared.application.bus.command.Command
import com.letgo.shared.infrastructure.bus.AsyncConsumer
import com.letgo.shared.infrastructure.InfrastructureService
import com.letgo.shared.infrastructure.bus.Consumer
import com.letgo.shared.infrastructure.bus.serialize.MessageSerializer

@InfrastructureService
class AsyncCommandConsumer(
    private val serializer: MessageSerializer<Command>,
    private val handlerFinder: CommandHandlerFinder,
    private val consumer: AsyncConsumer<String>,
) : Consumer {
    override fun consume() {
        consumer.start { message ->
            println("${Thread.currentThread().name} Started processing message: $message")
            val command = this.serializer.deserialize(message)
            this.handlerFinder.forCommand(command).handle(command)
            println("${Thread.currentThread().name} Finished processing of message: $message")
        }
    }
}
