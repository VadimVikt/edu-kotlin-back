package ru.vadim.edu.kotlin.back.rabbit.processor

import PrContext
import apiV1mapper
import com.rabbitmq.client.Channel
import com.rabbitmq.client.Delivery
import fromTransport
import kotlinx.datetime.Clock
import ru.vadim.edu.kotlin.back.api.v1.models.IRequest
import ru.vadim.edu.kotlin.back.rabbit.RabbitProcessorBase
import ru.vadim.edu.kotlin.back.rabbit.biz.PrProfileProcessor
import ru.vadim.edu.kotlin.back.rabbit.config.RabbitConfig
import ru.vadim.edu.kotlin.back.rabbit.config.RabbitExchangeConfiguration
import toTransport

class RabbitDirectProcessorV1(
    config: RabbitConfig,
    processorConfig: RabbitExchangeConfiguration,
    private val processor: PrProfileProcessor = PrProfileProcessor(),
) : RabbitProcessorBase(config, processorConfig) {

    private val context = PrContext()

    override suspend fun Channel.processMessage(message: Delivery) {
        context.apply {
            timeStart = Clock.System.now()
        }

        apiV1mapper.readValue(message.body, IRequest::class.java).run {
            context.fromTransport(this).also {
                println("TYPE: ${this::class.simpleName}")
            }
        }
        val response = processor.exec(context).run { context.toTransport() }
        apiV1mapper.writeValueAsBytes(response).also {
            println("Publishing $response to ${processorConfig.exchange} exchange for keyOut ${processorConfig.keyOut}")
            basicPublish(processorConfig.exchange, processorConfig.keyOut, null, it)
        }.also {
            println("published")
        }
    }

    override fun Channel.onError(e: Throwable) {
        e.printStackTrace()
//        context.state = MkplState.FAILING
//        context.addError(error = arrayOf(e.asMkplError()))
        val response = context.toTransport()
        apiV1mapper.writeValueAsBytes(response).also {
            basicPublish(processorConfig.exchange, processorConfig.keyOut, null, it)
        }
    }
}
