package com.letgo.shared.infrastructure.bus.queue

abstract class QueueHandler<T> {
    val main: Queue<T> = ArrayListQueue()
    val deadLetter: Queue<T> = ArrayListQueue()
}
