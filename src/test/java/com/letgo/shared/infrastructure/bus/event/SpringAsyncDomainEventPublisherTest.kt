package com.letgo.shared.infrastructure.bus.event

import com.letgo.shared.domain.AnEvent
import io.mockk.*
import org.junit.jupiter.api.Test
import org.springframework.context.ApplicationEventPublisher

private class SpringAsyncDomainEventPublisherTest {
    private val applicationEventPublisher: ApplicationEventPublisher = mockk()
    private val publisher = SpringAsyncDomainEventPublisher(applicationEventPublisher)

    @Test
    fun `It should pass all events to Spring's application event publisher`() {
        val anEvent = AnEvent()
        every { applicationEventPublisher.publishEvent(anEvent) } just Runs

        publisher.publish(listOf(anEvent, anEvent))

        verify(exactly = 2) { applicationEventPublisher.publishEvent(anEvent) }
    }
}
