package com.letgo.shared.infrastructure.bus.serialize

import com.letgo.shared.domain.DomainEvent
import com.letgo.shared.infrastructure.InfrastructureService
import org.json.JSONObject
import java.time.LocalDateTime
import java.util.UUID
import kotlin.reflect.KClass
import kotlin.reflect.KParameter
import kotlin.reflect.full.memberProperties
import kotlin.reflect.full.primaryConstructor

@InfrastructureService
class JSONDomainEventSerializer(
    private val commandClassFinder: DomainEventClassFinder,
) : MessageSerializer<DomainEvent> {
    override fun serialize(message: DomainEvent): String {
        val attributes = message::class.memberProperties.filter {
            it.name !in listOf("id", "occurredOn", "aggregateId", "type")
        }.associate {
            it.name to it.getter.call(message).toString()
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
        val kClass = json.getString("type").let { commandClassFinder.find(it) }
        val properties = json.getJSONObject("attributes").toMap().apply {
            putAll(
                mapOf(
                    "id" to json.getString("id"),
                    "aggregateId" to json.getString("aggregate_id"),
                    "occurredOn" to json.getString("occurred_on")
                )
            )
        }
        return domainEventInstance(kClass, properties)
    }

    private fun domainEventInstance(
        kClass: KClass<out DomainEvent>,
        attributes: Map<String, Any>,
    ): DomainEvent {
        val params = mutableMapOf<KParameter, Any?>()
        kClass.primaryConstructor!!.apply {
            this.parameters.forEach {
                when (it.name) {
                    "id" -> params[it] = UUID.fromString(attributes.getValue(it.name.toString()).toString())
                    "occurredOn" -> params[it] = LocalDateTime.parse(attributes.getValue(it.name.toString()).toString())
                    else -> params[it] = attributes.getValue(it.name.toString())
                }
            }
        }.also {
            return it.callBy(params)
        }
    }
}
