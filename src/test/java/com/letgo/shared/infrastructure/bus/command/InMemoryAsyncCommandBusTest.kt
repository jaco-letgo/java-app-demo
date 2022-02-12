package com.letgo.shared.infrastructure.bus.command

import com.letgo.shared.infrastructure.bus.queue.Queue
import io.mockk.*
import org.junit.jupiter.api.Test

private class InMemoryAsyncCommandBusTest {
    private val queue: Queue<String> = mockk()
    private val serializer = FakeSerializer()
    private val commandBus = InMemoryAsyncCommandBus(queue, serializer)

    @Test
    fun `It should serialize and enqueue a command`() {
        val serializedCommand = "OLAKEASE"

        every { queue.enqueue(serializedCommand) } just Runs

        commandBus.dispatch(ACommand(serializedCommand))

        verify { queue.enqueue(serializedCommand) }
    }
}
