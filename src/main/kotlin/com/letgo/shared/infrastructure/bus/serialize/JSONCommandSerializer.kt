package com.letgo.shared.infrastructure.bus.serialize

import com.letgo.shared.application.bus.command.Command
import com.letgo.shared.infrastructure.InfrastructureService
import org.json.JSONObject
import kotlin.reflect.KClass
import kotlin.reflect.full.memberProperties
import kotlin.reflect.full.primaryConstructor

@InfrastructureService
class JSONCommandSerializer(
    private val commandClassFinder: CommandClassFinder,
) : MessageSerializer<Command> {
    override fun serialize(message: Command): String {
        val attributes = message::class.memberProperties.associate {
            it.name to it.getter.call(message)
        }
        return """
            {
                "type":"${message::class.simpleName}",
                "attributes":${JSONObject(attributes)}
            }
        """.trimIndent()
    }

    override fun deserialize(message: String): Command {
        val json = JSONObject(message)
        val commandClass = json.getString("type").let { commandClassFinder.find(it) }
        val properties = json.getJSONObject("attributes").toMap()
        return commandInstance(commandClass, properties)
    }

    private fun commandInstance(commandClass: KClass<out Command>, properties: Map<String, Any>): Command {
        return commandClass.primaryConstructor!!.run {
            callBy(
                parameters.associateWith {
                    properties[it.name]
                }
            )
        }
    }
}
