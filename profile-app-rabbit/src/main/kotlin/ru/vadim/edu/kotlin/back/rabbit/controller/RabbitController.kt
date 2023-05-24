package ru.vadim.edu.kotlin.back.rabbit.controller

import kotlinx.coroutines.*
import ru.vadim.edu.kotlin.back.rabbit.RabbitProcessorBase

class RabbitController(
    private val processors : Set<RabbitProcessorBase>
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    private val limitedParallelismContext = Dispatchers.IO.limitedParallelism(1)

    private val scope = CoroutineScope(
        limitedParallelismContext + CoroutineName("thread-rabbitmq-controller")
    )

    fun start() = scope.launch {
        processors.forEach {
            launch(
                limitedParallelismContext + CoroutineName("thread-rabbitmq-controller")
            ) {
                try {
                    it.process()
                } catch (e: RuntimeException) {
                    // логируем, что-то делаем
                    e.printStackTrace()
                }
            }
        }
    }
}