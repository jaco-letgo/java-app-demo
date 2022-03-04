package com.letgo.shared.infrastructure.bus.serialize

import com.letgo.shared.domain.DomainEvent
import com.letgo.shared.infrastructure.InfrastructureService
import org.json.JSONObject
import java.time.LocalDateTime
import java.util.UUID
import kotlin.reflect.KClass
import kotlin.reflect.full.memberProperties
import kotlin.reflect.full.primaryConstructor

@InfrastructureService
class JSONDomainEventSerializer(
    private val domainEventClassFinder: DomainEventClassFinder,
) : MessageSerializer<DomainEvent> {
    override fun serialize(message: DomainEvent): String {
        val attributes = message::class.memberProperties.filter {
            it.name !in listOf("id", "occurredOn", "aggregateId", "type")
        }.associate {
            it.name to it.getter.call(message)
        }
        return """
            {
                "type":"${message.type}",
                "id":"${message.id}",
                "occurred_on":"${message.occurredOn}",
                "aggregate_id":"${message.aggregateId}",
                "attributes":${JSONObject(attributes)}
            }
        """.trimIndent()
    }

    override fun deserialize(message: String): DomainEvent {
        val json = JSONObject(message)
        val eventClass = json.getString("type").let { domainEventClassFinder.find(it) }
        val properties = json.getJSONObject("attributes").toMap().apply {
            putAll(
                mapOf(
                    "id" to json.getString("id"),
                    "aggregateId" to json.getString("aggregate_id"),
                    "occurredOn" to json.getString("occurred_on")
                )
            )
        }
        return domainEventInstance(eventClass, properties)
    }

    private fun domainEventInstance(eventClass: KClass<out DomainEvent>, properties: Map<String, Any>): DomainEvent {
        return eventClass.primaryConstructor!!.run {
            callBy(
                parameters.associateWith {
                    itsType(it.name, properties[it.name])
                }
            )
        }
    }

    private fun itsType(parameterName: String?, value: Any?) = when (parameterName) {
        "id" -> UUID.fromString(value.toString())
        "occurredOn" -> LocalDateTime.parse(value.toString())
        else -> value
    }
}
