package com.letgo.shared.infrastructure.bus.serialize

import com.letgo.shared.application.bus.command.Command
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

private const val MESSAGE =
    """
        {
            "type":"ACommand",
            "attributes":{"boolean":true,"integer":123,"string":"olakease","array":["ola","ke","ase"]}
        }
    """

private class JSONCommandSerializerTest {
    private val commandClassFinder: CommandClassFinder = mockk()
    private val serializer: JSONCommandSerializer = JSONCommandSerializer(commandClassFinder)
    private val command = ACommand(
        nullable = null,
        string = "olakease",
        integer = 123,
        boolean = true,
        array = listOf("ola", "ke", "ase"),
    )

    @Test
    fun `It should serialize a command into JSON`() {
        assertEquals(MESSAGE.trimIndent(), serializer.serialize(command))
    }

    @Test
    fun `It should deserialize a JSON into a command`() {
        every { commandClassFinder.find("ACommand") } answers { ACommand::class }
        val deserialize = serializer.deserialize(MESSAGE)
        assertEquals(command, deserialize)
    }
}

data class ACommand(
    val nullable: String?,
    val string: String,
    val integer: Int,
    val boolean: Boolean,
    val array: List<String>,
) : Command
