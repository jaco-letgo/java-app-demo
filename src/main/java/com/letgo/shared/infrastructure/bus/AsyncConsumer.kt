package com.letgo.shared.infrastructure.bus

import com.letgo.shared.infrastructure.InfrastructureService
import com.letgo.shared.infrastructure.bus.queue.QueueHandler
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.yield
import javax.annotation.PreDestroy
import kotlin.coroutines.CoroutineContext

private const val MAX_RETRIES = 5
private const val WORKERS = 10

@InfrastructureService
class AsyncConsumer<T>(
    private val queueHandler: QueueHandler<T>,
) : CoroutineScope {
    private val retries: HashMap<String, Int> = hashMapOf()
    private val supervisorJob = SupervisorJob()
    override val coroutineContext: CoroutineContext = Dispatchers.Default + supervisorJob

    fun start(job: (message: T) -> Unit) {
        launch {
            val channel = Channel<T>()
            launchMessageReceiver(channel)
            repeat(WORKERS) {
                launchWorker(channel, job)
            }
        }
    }

    @PreDestroy
    fun stop() {
        supervisorJob.cancel()
    }

    private fun CoroutineScope.launchMessageReceiver(channel: SendChannel<T>) = launch {
        repeatUntilCancelled {
            queueHandler.main.dequeue()?.let { message ->
                channel.send(message)
                println("${Thread.currentThread().name} Message dequeued: $message, total messages: ${queueHandler.main.count}")
            }
        }
    }

    private fun CoroutineScope.launchWorker(channel: ReceiveChannel<T>, process: (message: T) -> Unit) =
        launch {
            repeatUntilCancelled {
                for (message in channel) {
                    try {
                        process(message)
                    } catch (exception: Exception) {
                        println("${Thread.currentThread().name} exception trying to process message $message: ${exception.message}")
                        val messageKey = message.toString()
                        retries[messageKey] = 1 + (retries[messageKey] ?: 0)
                        if ((retries[messageKey] ?: 1) <= MAX_RETRIES) {
                            println("${Thread.currentThread().name} re-enqueuing, current failures: ${retries[messageKey]}")
                            queueHandler.main.enqueue(message)
                        } else {
                            println("${Thread.currentThread().name} sending to dead letter, current failures: ${retries[messageKey]}")
                            retries.remove(messageKey)
                            queueHandler.deadLetter.enqueue(message)
                        }
                    }
                }
            }
        }

    private suspend fun CoroutineScope.repeatUntilCancelled(block: suspend () -> Unit) {
        while (isActive) {
            try {
                block()
                yield()
            } catch (exception: CancellationException) {
                println("coroutine on ${Thread.currentThread().name} cancelled")
            } catch (exception: Exception) {
                println("${Thread.currentThread().name} failed with {$exception}. Retrying...")
                exception.printStackTrace()
            }
        }

        println("coroutine on ${Thread.currentThread().name} exiting")
    }
}
