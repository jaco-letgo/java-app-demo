package com.letgo.shared.infrastructure.bus.command

import com.letgo.shared.infrastructure.bus.queue.QueueHandler
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test

private class InMemoryAsyncCommandBusTest {
    private val queueHandler: QueueHandler<String> = mockk()
    private val commandBus = InMemoryAsyncCommandBus(queueHandler, FakeCommandSerializer())

    @Test
    fun `It should serialize and enqueue a command`() {
        val serializedCommand = "OLAKEASE"

        every { queueHandler.main.enqueue(serializedCommand) } just Runs

        commandBus.dispatch(ACommand(serializedCommand))

        verify { queueHandler.main.enqueue(serializedCommand) }
    }
}
