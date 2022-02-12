package com.letgo.shared.infrastructure.bus.command

import com.letgo.shared.application.bus.command.Command
import com.letgo.shared.infrastructure.bus.serialize.MessageSerializer

internal class FakeSerializer : MessageSerializer<Command> {
    override fun serialize(message: Command): String =
        if (message is ACommand) message.parameter else throw Exception("bad command")
    override fun deserialize(message: String): ACommand = ACommand(message)
}
