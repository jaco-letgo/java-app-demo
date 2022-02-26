package com.letgo.shared.infrastructure.bus.queue

class ArrayListQueue<T> : Queue<T> {
    private val list = arrayListOf<T>()

    override fun enqueue(message: T) {
        list.add(message)
    }

    override fun dequeue(): T? = if (isEmpty) null else list.removeAt(0)

    override val count: Int
        get() = list.size

    override fun peek(): T? = list.getOrNull(0)
}
