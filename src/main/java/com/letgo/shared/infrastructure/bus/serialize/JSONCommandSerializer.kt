package com.letgo.shared.infrastructure.bus.serialize

import com.letgo.shared.application.bus.command.Command
import com.letgo.shared.infrastructure.InfrastructureService
import org.json.JSONObject
import kotlin.reflect.KClass
import kotlin.reflect.KParameter
import kotlin.reflect.full.memberProperties
import kotlin.reflect.full.primaryConstructor

@InfrastructureService
class JSONCommandSerializer(
    private val commandClassFinder: CommandClassFinder
) : MessageSerializer<Command> {
    override fun serialize(message: Command): String {
        val map = mutableMapOf<String, String>()
        message::class.memberProperties.forEach {
            map[it.name] = it.getter.call(message) as String
        }
        return "{\"type\":\"${message::class.simpleName}\",\"attributes\":${JSONObject(map)}}"
    }

    override fun deserialize(message: String): Command {
        val json = JSONObject(message)
        val commandClass = json.getString("type").let { commandClassFinder.find(it) }
        val attributes = json.getJSONObject("attributes").toMap()
        return createCommand(commandClass, attributes)
    }

    private fun createCommand(commandClass: KClass<out Command>, attributes: Map<String, Any>): Command {
        val params = mutableMapOf<KParameter, Any?>()
        commandClass.primaryConstructor!!.apply {
            this.parameters.forEach { params[it] = attributes.getValue(it.name.toString()) }
        }.also { return it.callBy(params) }
    }
}
