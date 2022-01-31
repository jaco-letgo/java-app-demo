package com.letgo.shared.infrastructure.bus.command

import com.letgo.shared.application.bus.command.Command
import com.letgo.shared.application.bus.command.CommandBus
import com.letgo.shared.infrastructure.InfrastructureService
import com.letgo.shared.infrastructure.bus.queue.Queue
import com.letgo.shared.infrastructure.bus.serialize.MessageSerializer

@InfrastructureService
class InMemoryAsyncCommandBus(
    private val queue: Queue<String>,
    private val serializer: MessageSerializer<Command>
) : CommandBus {
    override fun dispatch(command: Command) {
        queue.enqueue(serializer.serialize(command))
    }
}
