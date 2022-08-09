package com.letgo.shared.infrastructure.bus.queue

interface Queue<T> {
    fun enqueue(message: T)
    fun dequeue(): T?
    val count: Int
    val isEmpty: Boolean
        get() = count == 0
    fun peek(): T?
}
