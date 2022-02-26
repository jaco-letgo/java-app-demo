package com.letgo.shared.infrastructure.bus.command

import com.letgo.shared.application.bus.command.CommandHandler
import com.letgo.shared.infrastructure.bus.queue.Queue
import com.letgo.shared.infrastructure.bus.queue.QueueHandler
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.lang.Thread.sleep

private class AsyncCommandConsumerTest {
    private val queueHandler = QueueHandler<String>()
    private val queue: Queue<String> = queueHandler.main
    private val deadLetter: Queue<String> = queueHandler.deadLetter
    private val serializer = FakeCommandSerializer()

    @Test
    fun `It should dequeue, deserialize and pass a command into its handler`() {
        val serializedMessage = "olakease"
        val handler = SpyCommandHandler(ACommand(serializedMessage))
        val consumer = AsyncCommandConsumer(queueHandler, serializer, CommandHandlerFinder(listOf(handler)))

        queue.enqueue(serializedMessage)

        consumer.consume()
        sleep(100)
        consumer.stop()

        assertTrue(queue.isEmpty)
        assertTrue(deadLetter.isEmpty)
        assertTrue(handler.hasBeenCalled())
    }

    @Test
    fun `It should re-enqueue a message when handler throws an exception`() {
        val serializedMessage = "olakease"
        val handler = FailingCommandHandler()
        val consumer = AsyncCommandConsumer(queueHandler, serializer, CommandHandlerFinder(listOf(handler)))

        queue.enqueue(serializedMessage)

        consumer.consume()
        sleep(100)
        consumer.stop()

        assertTrue(queue.isEmpty)
        assertFalse(deadLetter.isEmpty)
    }

    private class FailingCommandHandler : CommandHandler<ACommand> {
        override fun handle(command: ACommand) {
            throw Exception("something went wrong")
        }
    }
}
