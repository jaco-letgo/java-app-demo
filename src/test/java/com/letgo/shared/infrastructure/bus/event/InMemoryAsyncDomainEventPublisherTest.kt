package com.letgo.shared.infrastructure.bus.event

import com.letgo.shared.domain.AnEvent
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test

private class InMemoryAsyncDomainEventPublisherTest {
    private val queueHandler: DomainEventQueueHandler = mockk()
    private val eventPublisher = InMemoryAsyncDomainEventPublisher(queueHandler)

    @Test
    fun `It should enqueue a domain event`() {
        val event = AnEvent()

        every { queueHandler.main.enqueue(event) } just Runs

        eventPublisher.publish(listOf(event))

        verify { queueHandler.main.enqueue(event) }
    }
}
