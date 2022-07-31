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

    private fun launchMessageReceiver(channel: SendChannel<T>) = launch {
        repeatUntilCancelled {
            queueHandler.main.dequeue()?.let { channel.send(it) }
        }
    }

    private fun launchWorker(channel: ReceiveChannel<T>, process: (message: T) -> Unit) =
        launch {
            repeatUntilCancelled {
                for (message in channel) {
                    runCatching {
                        process(message)
                    }.onFailure {
                        queueHandler.handleFailure(message)
                    }
                }
            }
        }

    private suspend fun repeatUntilCancelled(block: suspend () -> Unit) {
        while (isActive) {
            runCatching {
                block()
                yield()
            }.onFailure { exception ->
                when (exception) {
                    is CancellationException -> println("coroutine on ${Thread.currentThread().name} cancelled")
                    else -> exception.let {
                        println("${Thread.currentThread().name} failed with {$it}. Retrying...")
                        it.printStackTrace()
                    }
                }
            }
        }

        println("coroutine on ${Thread.currentThread().name} exiting")
    }
}
