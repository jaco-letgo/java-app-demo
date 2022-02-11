package com.letgo.shared.infrastructure.bus.command

import com.letgo.shared.application.bus.command.Command
import com.letgo.shared.application.bus.command.CommandHandler
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

private class InMemorySyncCommandBusTest {
    @Test
    fun `It should dispatch Command objects`() {
        val command = BarCommand("olakease")
        val expectedHandlerToBeCalled = BarCommandHandler(command)
        val commandBus = InMemorySyncCommandBus(
            CommandHandlerFinder(
                listOf(
                    FooCommandHandler(command),
                    expectedHandlerToBeCalled
                )
            )
        )

        commandBus.dispatch(command)

        assertTrue(expectedHandlerToBeCalled.hasBeenCalled)
    }

    @Test
    fun `It should throw an exception when there is no handler for the given Command object`() {
        val command = BarCommand("olakease")
        val commandBus = InMemorySyncCommandBus(
            CommandHandlerFinder(
                listOf(
                    FooCommandHandler(command)
                )
            )
        )

        assertThrows<Exception> { commandBus.dispatch(command) }.run {
            assertEquals("No handler found for ${command::class}", message)
        }
    }

    private data class FooCommand(private val property: String) : Command
    private data class BarCommand(private val property: String) : Command

    private class FooCommandHandler(
        private val expectedCommand: Command,
        var hasBeenCalled: Boolean = false
    ) : CommandHandler<FooCommand> {
        override fun handle(command: FooCommand) {
            hasBeenCalled = true
            assertEquals(expectedCommand, command)
        }
    }

    private class BarCommandHandler(
        private val expectedCommand: Command,
        var hasBeenCalled: Boolean = false
    ) : CommandHandler<BarCommand> {
        override fun handle(command: BarCommand) {
            hasBeenCalled = true
            assertEquals(expectedCommand, command)
        }
    }
}
