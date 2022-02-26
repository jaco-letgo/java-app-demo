package com.letgo.shared.infrastructure.bus.queue

import com.letgo.shared.infrastructure.InfrastructureService

@InfrastructureService
class QueueHandler<T> {
    val main: Queue<T> = ArrayListQueue()
    val deadLetter: Queue<T> = ArrayListQueue()
}
