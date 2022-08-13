package com.letgo.shared.infrastructure.bus.serialize

import com.fasterxml.jackson.databind.node.ObjectNode
import com.fasterxml.jackson.module.kotlin.convertValue
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.letgo.shared.application.bus.command.Command
import com.letgo.shared.infrastructure.InfrastructureService

@InfrastructureService
class JsonCommandSerializer(
    private val commandClassFinder: CommandClassFinder,
) : MessageSerializer<Command> {
    private val mapper = jacksonObjectMapper()

    override fun serialize(message: Command): String = mapper.writeValueAsString(
        CommandWrapper(
            type = message::class.simpleName!!,
            attributes = mapper.convertValue(message)
        )
    )

    override fun deserialize(message: String): Command = mapper.readValue<CommandWrapper>(message).let {
        mapper.convertValue(it.attributes, it.type.let { type -> commandClassFinder.find(type) }.java)
    }

    private data class CommandWrapper(val type: String, val attributes: ObjectNode)
}
