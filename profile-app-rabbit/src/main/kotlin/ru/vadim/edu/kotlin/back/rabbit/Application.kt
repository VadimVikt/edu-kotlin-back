package ru.vadim.edu.kotlin.back.rabbit

import ru.vadim.edu.kotlin.back.rabbit.biz.PrProfileProcessor
import ru.vadim.edu.kotlin.back.rabbit.config.RabbitConfig
import ru.vadim.edu.kotlin.back.rabbit.config.RabbitExchangeConfiguration
import ru.vadim.edu.kotlin.back.rabbit.controller.RabbitController
import ru.vadim.edu.kotlin.back.rabbit.processor.RabbitDirectProcessorV1

fun main() {
    val config = RabbitConfig()
    val prProcessor = PrProfileProcessor()

    val producerConfigV1 = RabbitExchangeConfiguration(
        keyIn = "in-v1",
        keyOut = "out-v1",
        exchange = "transport-exchange",
        queue = "v1-queue",
        consumerTag = "v1-consumer",
        exchangeType = "direct"
    )

    val processor by lazy {
        RabbitDirectProcessorV1(
            config = config,
            processorConfig = producerConfigV1,
            processor = prProcessor
        )
    }

    val controller by lazy {
        RabbitController(
            processors = setOf(processor)
        )
    }
    controller.start()

}
