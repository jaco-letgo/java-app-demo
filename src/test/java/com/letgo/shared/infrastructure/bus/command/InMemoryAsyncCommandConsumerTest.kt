package com.letgo.shared.infrastructure.bus.command

import com.letgo.shared.application.bus.command.CommandHandler
import com.letgo.shared.infrastructure.bus.queue.Queue
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertSame
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.lang.Thread.sleep

private class InMemoryAsyncCommandConsumerTest {
    private val queueHandler = SerializedCommandQueueHandler()
    private val queue: Queue<String> = queueHandler.main
    private val deadLetter: Queue<String> = queueHandler.deadLetter
    private val serializer = FakeCommandSerializer()

    @Test
    fun `It should dequeue, deserialize and pass a command into its handler`() {
        val serializedMessage = "olakease"
        val handler = SpyCommandHandler(ACommand(serializedMessage))
        val consumer = InMemoryAsyncCommandConsumer(serializer, CommandHandlerFinder(listOf(handler)), queueHandler)

        queue.enqueue(serializedMessage)

        consumer.consume()
        sleep(100)

        assertTrue(queue.isEmpty)
        assertTrue(deadLetter.isEmpty)
        assertTrue(handler.hasBeenCalled())
    }

    @Test
    fun `It should re-enqueue a message when handler throws an exception`() {
        val serializedMessage = "olakease"
        val handler = FailingCommandHandler()
        val consumer = InMemoryAsyncCommandConsumer(serializer, CommandHandlerFinder(listOf(handler)), queueHandler)

        queue.enqueue(serializedMessage)

        consumer.consume()
        sleep(100)

        assertTrue(queue.isEmpty)
        assertFalse(deadLetter.isEmpty)
        assertSame(serializedMessage, deadLetter.peek())
    }

    private class FailingCommandHandler : CommandHandler<ACommand> {
        override fun handle(command: ACommand) {
            throw Exception("something went wrong")
        }
    }
}
