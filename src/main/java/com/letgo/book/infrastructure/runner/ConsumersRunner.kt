package com.letgo.book.infrastructure.runner

import com.letgo.shared.infrastructure.InfrastructureService
import com.letgo.shared.infrastructure.bus.Consumer
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner

@InfrastructureService
class ConsumersRunner(
    private val consumers: List<Consumer>
) : ApplicationRunner {
    override fun run(args: ApplicationArguments?) {
        consumers.forEach {
            Thread {
                while (true) {
                    runBlocking {
                        launch {
                            delay(100)
                            it.consume()
                        }
                    }
                }
            }.start()
        }
    }
}
