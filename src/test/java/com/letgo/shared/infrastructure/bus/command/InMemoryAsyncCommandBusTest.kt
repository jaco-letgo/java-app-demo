package com.letgo.shared.infrastructure.bus.command

import com.letgo.shared.application.bus.command.Command
import com.letgo.shared.infrastructure.bus.serialize.MessageSerializer
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Test

private class InMemoryAsyncCommandBusTest {
    private val queueHandler = SerializedCommandQueueHandler(0)
    private val queue = queueHandler.main
    private val serializer: MessageSerializer<Command> = mockk()
    private val commandBus = InMemoryAsyncCommandBus(queueHandler, serializer)

    @Test
    fun `It should serialize and enqueue a command`() {
        val serializedCommand = "OLAKEASE"
        val command = ACommand()

        every { serializer.serialize(command) } answers { serializedCommand }

        commandBus.dispatch(command)

        assertFalse(queue.isEmpty)
        assertEquals(command, queue.peek())
    }
}
