package com.letgo.shared.infrastructure.bus.queue

import org.springframework.stereotype.Component

@Component
class ArrayListQueue : Queue<String> {
    private val list = arrayListOf<String>()

    override fun enqueue(message: String) {
        list.add(message)
        println("Message enqueued: $message, total messages: $count")
        println("Processed on: ${Thread.currentThread().name}")
    }

    override fun dequeue(): String? {
        println("Message dequeued: ${peek()}, total messages: ${count - 1}")
        println("Processed on: ${Thread.currentThread().name}")
        return if (isEmpty) null else list.removeAt(0)
    }

    override val count: Int
        get() = list.size

    override fun peek(): String? = list.getOrNull(0)
}
