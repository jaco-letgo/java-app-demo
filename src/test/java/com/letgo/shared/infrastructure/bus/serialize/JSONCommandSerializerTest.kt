package com.letgo.shared.infrastructure.bus.serialize

import com.letgo.shared.application.bus.command.Command
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

private const val MESSAGE =
    "{\"type\":\"ACommand\",\"attributes\":{\"third\":\"ase\",\"first\":\"ola\",\"second\":\"ke\"}}"

private class JSONCommandSerializerTest {
    private val commandClassFinder: CommandClassFinder = mockk()
    private val serializer: JSONCommandSerializer = JSONCommandSerializer(commandClassFinder)
    private val command = ACommand("ola", "ke", "ase")

    @Test
    fun `It should serialize a command into JSON`() {
        assertEquals(MESSAGE, serializer.serialize(command))
    }

    @Test
    fun `It should deserialize a JSON into a command`() {
        every { commandClassFinder.find("ACommand") } answers { ACommand::class }
        assertEquals(this.command, serializer.deserialize(MESSAGE))
    }
}

data class ACommand(val first: String, val second: String, val third: String) : Command
