package com.letgo.shared.infrastructure.bus.queue

class ArrayListQueue : Queue<String> {
    private val list = arrayListOf<String>()

    override fun enqueue(message: String) {
        list.add(message)
    }

    override fun dequeue(): String? = if (isEmpty) null else list.removeAt(0)

    override val count: Int
        get() = list.size

    override fun peek(): String? = list.getOrNull(0)
}
