package com.letgo.shared.infrastructure.bus.command

import com.letgo.shared.application.bus.command.Command
import com.letgo.shared.application.bus.command.CommandHandler
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

private class CommandHandlerFinderTest {
    @Test
    fun `It should return the correct handler for a given command`() {
        val expectedHandler = BarCommandHandler()
        val finder = CommandHandlerFinder(
            listOf(
                FooCommandHandler(),
                expectedHandler
            )
        )

        assertEquals(expectedHandler, finder.forCommand(BarCommand()))
    }

    @Test
    fun `It should throw an exception when there is no handler for the given Command object`() {
        val command = BarCommand()
        val finder = CommandHandlerFinder(listOf(FooCommandHandler()))

        assertThrows<NoSuchElementException> { finder.forCommand(command) }.run {
            assertEquals("No handler found for ${command::class}", message)
        }
    }

    private class FooCommand : Command
    private class BarCommand : Command

    private class FooCommandHandler : CommandHandler<FooCommand> {
        override fun handle(command: FooCommand) {}
    }

    private class BarCommandHandler : CommandHandler<BarCommand> {
        override fun handle(command: BarCommand) {}
    }
}
