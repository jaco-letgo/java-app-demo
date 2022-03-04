package com.letgo.shared.infrastructure.bus.command

import com.letgo.shared.application.bus.command.Command
import com.letgo.shared.application.bus.command.CommandHandler
import com.letgo.shared.infrastructure.bus.queue.Queue
import com.letgo.shared.infrastructure.bus.serialize.MessageSerializer
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertSame
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.lang.Thread.sleep

private class InMemoryAsyncCommandConsumerTest {
    private val queueHandler = SerializedCommandQueueHandler(0)
    private val consumer = SerializedCommandAsyncConsumer(queueHandler)
    private val queue: Queue<String> = queueHandler.main
    private val deadLetter: Queue<String> = queueHandler.deadLetter
    private val serializer: MessageSerializer<Command> = mockk()

    @Test
    fun `It should dequeue, deserialize and pass a command into its handler`() {
        val serializedMessage = "olakease"
        val command = ACommand()
        val handler = SpyCommandHandler(command)
        val consumer = InMemoryAsyncCommandConsumer(serializer, CommandHandlerFinder(listOf(handler)), consumer)

        queue.enqueue(serializedMessage)
        every { serializer.deserialize(serializedMessage) } answers { command }

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
        val consumer = InMemoryAsyncCommandConsumer(serializer, CommandHandlerFinder(listOf(handler)), consumer)

        queue.enqueue(serializedMessage)
        every { serializer.deserialize(serializedMessage) } answers { ACommand() }

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
