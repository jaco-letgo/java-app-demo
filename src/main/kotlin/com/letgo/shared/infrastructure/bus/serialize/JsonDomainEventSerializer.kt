package com.letgo.shared.infrastructure.bus.serialize

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.node.ObjectNode
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.convertValue
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.letgo.shared.domain.DomainEvent
import com.letgo.shared.infrastructure.InfrastructureService

@InfrastructureService
class JsonDomainEventSerializer(
    private val domainEventClassFinder: DomainEventClassFinder,
) : MessageSerializer<DomainEvent> {
    private val mapper = jacksonObjectMapper().apply {
        registerModule(JavaTimeModule())
        disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
    }

    override fun serialize(message: DomainEvent): String {
        return mapper.writeValueAsString(
            EventWrapper(
                type = message.type,
                id = message.id.toString(),
                occurredOn = message.occurredOn.toString(),
                aggregateId = message.aggregateId,
                attributes = mapper.convertValue<ObjectNode>(message).apply {
                    removeAll {
                        it.asText() == message.type ||
                            it.asText() == message.aggregateId ||
                            it.asText() == message.id.toString() ||
                            it.asText() == message.occurredOn.toString()
                    }
                }
            )
        )
    }

    override fun deserialize(message: String): DomainEvent {
        return mapper.readValue<EventWrapper>(message).let {
            val event = it.attributes.apply {
                put("type", it.type)
                put("id", it.id)
                put("occurredOn", it.occurredOn)
                put("aggregateId", it.aggregateId)
            }
            mapper.convertValue(event, it.type.let { type -> domainEventClassFinder.find(type) }.java)
        }
    }

    private data class EventWrapper(
        val type: String,
        val id: String,
        @JsonProperty("occurred_on") val occurredOn: String,
        @JsonProperty("aggregate_id") val aggregateId: String,
        val attributes: ObjectNode,
    )
}
