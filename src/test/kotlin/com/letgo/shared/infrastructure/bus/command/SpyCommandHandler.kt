package com.letgo.shared.infrastructure.bus.command

import com.letgo.shared.application.bus.command.CommandHandler
import org.junit.jupiter.api.Assertions.assertEquals

internal class SpyCommandHandler(
    private val expectedCommand: ACommand,
) : CommandHandler<ACommand> {
    private var hasBeenCalled = false

    override fun handle(command: ACommand) {
        hasBeenCalled = true
        assertEquals(expectedCommand, command)
    }

    fun hasBeenCalled(): Boolean = hasBeenCalled
}
