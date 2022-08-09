package com.letgo.shared.infrastructure.bus.queue

abstract class QueueHandler<T>(
    private val maxRetries: Int,
) {
    val main: Queue<T> = ArrayListQueue()
    val deadLetter: Queue<T> = ArrayListQueue()
    private val retries: HashMap<String, Int> = hashMapOf()

    fun handleFailure(message: T) {
        val messageKey = message.toString()
        retries[messageKey] = 1 + (retries[messageKey] ?: 0)
        if ((retries[messageKey] ?: 1) <= maxRetries) {
            main.enqueue(message)
        } else {
            retries.remove(messageKey)
            deadLetter.enqueue(message)
        }
    }
}
