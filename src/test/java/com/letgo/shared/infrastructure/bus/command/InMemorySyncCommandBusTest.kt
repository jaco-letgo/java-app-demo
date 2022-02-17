package com.letgo.shared.infrastructure.bus.command

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

private class InMemorySyncCommandBusTest {
    @Test
    fun `It should dispatch Command objects`() {
        val command = ACommand("olakease")
        val handler = SpyCommandHandler(command)
        val commandBus = InMemorySyncCommandBus(CommandHandlerFinder(listOf(handler)))

        commandBus.dispatch(command)

        assertTrue(handler.hasBeenCalled)
    }
}
