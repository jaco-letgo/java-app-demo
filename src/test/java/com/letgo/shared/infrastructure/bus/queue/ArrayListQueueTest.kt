package com.letgo.shared.infrastructure.bus.queue

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

private class ArrayListQueueTest {
    private val queue = ArrayListQueue()

    @Test
    fun `It should enqueue messages`() {
        val message = "olakease"
        queue.enqueue(message)

        assertEquals(1, queue.count)
        assertEquals(message, queue.peek())
    }

    @Test
    fun `It should dequeue messages`() {
        val message = "olakease"
        queue.enqueue(message)
        assertEquals(1, queue.count)

        val dequeuedMessage = queue.dequeue()
        assertEquals(dequeuedMessage, message)
        assertEquals(0, queue.count)
    }

    @Test
    fun `It should peek messages`() {
        val message = "olakease"
        queue.enqueue(message)
        assertEquals(1, queue.count)

        val peekedMessage = queue.peek()
        assertEquals(peekedMessage, message)
        assertEquals(1, queue.count)
    }
}
