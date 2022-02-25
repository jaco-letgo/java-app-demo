package com.letgo.shared.infrastructure.bus.command

import com.letgo.shared.application.bus.command.Command
import com.letgo.shared.infrastructure.InfrastructureService
import com.letgo.shared.infrastructure.bus.Consumer
import com.letgo.shared.infrastructure.bus.queue.QueueHandler
import com.letgo.shared.infrastructure.bus.serialize.MessageSerializer
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
class AsyncCommandConsumer(
    private val queueHandler: QueueHandler,
    private val serializer: MessageSerializer<Command>,
    private val handlerFinder: CommandHandlerFinder,
) : Consumer, CoroutineScope {
    private val retries: HashMap<String, Int> = hashMapOf()
    private val supervisorJob = SupervisorJob()
    override val coroutineContext: CoroutineContext = Dispatchers.Default + supervisorJob

    override fun consume() {
        launch {
            val channel = Channel<String>()
            launchMessageReceiver(channel)
            repeat(WORKERS) {
                launchWorker(channel)
            }
        }
    }

    @PreDestroy
    fun stop() {
        supervisorJob.cancel()
    }

    private fun CoroutineScope.launchMessageReceiver(channel: SendChannel<String>) = launch {
        repeatUntilCancelled {
            queueHandler.main.dequeue()?.let { message ->
                channel.send(message)
                println("${Thread.currentThread().name} Message dequeued: $message, total messages: ${queueHandler.main.count}")
            }
        }
    }

    private fun CoroutineScope.launchWorker(channel: ReceiveChannel<String>) = launch {
        repeatUntilCancelled {
            for (message in channel) {
                try {
                    processMessage(message)
                } catch (ex: Exception) {
                    println("${Thread.currentThread().name} exception trying to process message $message: ${ex.message}")
                    retries[message] = 1 + (retries[message] ?: 0)
                    if ((retries[message] ?: 1) <= MAX_RETRIES) {
                        println("${Thread.currentThread().name} re-enqueuing, current failures: ${retries[message]}")
                        queueHandler.main.enqueue(message)
                    } else {
                        println("${Thread.currentThread().name} sending to dead letter, current failures: ${retries[message]}")
                        queueHandler.deadLetter.enqueue(message)
                    }
                }
            }
        }
    }

    private fun processMessage(message: String) {
        println("${Thread.currentThread().name} Started processing message: $message")
        val command = serializer.deserialize(message)
        handlerFinder.forCommand(command).handle(command)
        println("${Thread.currentThread().name} Finished processing of message: $message")
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
