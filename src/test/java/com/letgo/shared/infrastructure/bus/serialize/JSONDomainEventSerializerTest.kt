package com.letgo.shared.infrastructure.bus.serialize

import com.letgo.shared.domain.DomainEvent
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.time.LocalDateTime
import java.util.UUID

private const val MESSAGE =
    """
        {
            "type":"AnEvent",
            "id":"3d271aea-ee85-45b0-961d-80b3e9ff2db7",
            "occurred_on":"2022-03-02T17:12:49.004349",
            "aggregate_id":"1809d57e-8c94-492f-9e34-ed6ca4b60f06",
            "attributes":{"third":"ase","first":"ola","second":"ke"}
        }
    """

private class JSONDomainEventSerializerTest {
    private val classFinder: DomainEventClassFinder = mockk()
    private val serializer = JSONDomainEventSerializer(classFinder)
    private val event = AnEvent(
        first = "ola",
        second = "ke",
        third = "ase",
        id = UUID.fromString("3d271aea-ee85-45b0-961d-80b3e9ff2db7"),
        aggregateId = "1809d57e-8c94-492f-9e34-ed6ca4b60f06",
        occurredOn = LocalDateTime.parse("2022-03-02T17:12:49.004349")
    )

    @Test
    fun `It should serialize a command into JSON`() {
        assertEquals(MESSAGE.trimIndent(), serializer.serialize(event))
    }

    @Test
    fun `It should deserialize a JSON into a command`() {
        every { classFinder.find("AnEvent") } answers { AnEvent::class }
        assertEquals(event, serializer.deserialize(MESSAGE))
    }
}

data class AnEvent(
    override val id: UUID,
    override val aggregateId: String,
    override val occurredOn: LocalDateTime,
    val first: String,
    val second: String,
    val third: String,
) : DomainEvent
