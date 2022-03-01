package com.letgo.shared.infrastructure.bus

import com.letgo.shared.infrastructure.bus.queue.QueueHandler
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineDispatcher
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

abstract class AsyncConsumer<T>(
    private val queueHandler: QueueHandler<T>,
    private val numberOfWorkers: Int,
    dispatcher: CoroutineDispatcher = Dispatchers.Default,
) : CoroutineScope {
    private val supervisorJob = SupervisorJob()
    override val coroutineContext: CoroutineContext = dispatcher + supervisorJob

    fun start(job: (message: T) -> Unit) {
        launch {
            val channel = Channel<T>()
            launchMessageReceiver(channel)
            repeat(numberOfWorkers) {
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
            queueHandler.main.dequeue()?.let { channel.send(it) }
        }
    }

    private fun CoroutineScope.launchWorker(channel: ReceiveChannel<T>, process: (message: T) -> Unit) =
        launch {
            repeatUntilCancelled {
                for (message in channel) {
                    try {
                        process(message)
                    } catch (exception: Exception) {
                        queueHandler.handleFailure(message)
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
