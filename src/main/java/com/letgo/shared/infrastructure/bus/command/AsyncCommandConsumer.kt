package com.letgo.shared.infrastructure.bus.command

import com.letgo.shared.application.bus.command.Command
import com.letgo.shared.infrastructure.InfrastructureService
import com.letgo.shared.infrastructure.bus.Consumer
import com.letgo.shared.infrastructure.bus.queue.Queue
import com.letgo.shared.infrastructure.bus.serialize.MessageSerializer

@InfrastructureService
class AsyncCommandConsumer(
    private val queue: Queue<String>,
    private val serializer: MessageSerializer<Command>,
    private val handlerFinder: CommandHandlerFinder
) : Consumer {
    override fun consume() {
        if (!queue.isEmpty) {
            val message = queue.dequeue()!!
            val command = serializer.deserialize(message)
            val handler = handlerFinder.forCommand(command)
            try {
                handler.handle(command)
            } catch (exception: Throwable) {
                // I should enqueue into a retry and/or dead letter queue
                queue.enqueue(message)
            }
        }
    }
}
