package com.letgo.shared.infrastructure.bus.command

import com.letgo.shared.application.bus.command.CommandHandler
import com.letgo.shared.infrastructure.bus.queue.Queue
import io.mockk.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class AsyncCommandConsumerTest {
    private val queue: Queue<String> = mockk()
    private val serializer = FakeSerializer()

    @Test
    fun `It should dequeue, deserialize and pass a command into it's handler`() {
        val serializedMessage = "olakease"
        val handler = SpyCommandHandler(ACommand(serializedMessage))
        val consumer = AsyncCommandConsumer(queue, serializer, CommandHandlerFinder(listOf(handler)))

        every { queue.isEmpty } returns false
        every { queue.dequeue() } answers { serializedMessage }

        consumer.consume()

        verify { queue.dequeue() }
        assertTrue(handler.hasBeenCalled())
    }

    @Test
    fun `It should re-enqueue a message when handler throws an exception`() {
        val serializedMessage = "olakease"
        val consumer = AsyncCommandConsumer(queue, serializer, CommandHandlerFinder(listOf(FailingCommandHandler())))

        every { queue.isEmpty } returns false
        every { queue.dequeue() } answers { serializedMessage }
        every { queue.enqueue(serializedMessage) } just Runs

        consumer.consume()

        verify { queue.dequeue() }
        verify { queue.enqueue(serializedMessage) }
    }

    private class FailingCommandHandler : CommandHandler<ACommand> {
        override fun handle(command: ACommand) {
            throw Exception("something went wrong")
        }
    }
}
