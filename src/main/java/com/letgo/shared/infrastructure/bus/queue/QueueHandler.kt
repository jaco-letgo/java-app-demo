package com.letgo.shared.infrastructure.bus.queue

import com.letgo.shared.infrastructure.InfrastructureService

@InfrastructureService
class QueueHandler {
    val main: Queue<String> = ArrayListQueue()
    val deadLetter: Queue<String> = ArrayListQueue()
}
