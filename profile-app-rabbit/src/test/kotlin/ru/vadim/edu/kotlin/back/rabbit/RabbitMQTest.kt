package ru.vadim.edu.kotlin.back.rabbit

import apiV1mapper
import com.fasterxml.jackson.annotation.JsonProperty
import com.rabbitmq.client.CancelCallback
import com.rabbitmq.client.ConnectionFactory
import com.rabbitmq.client.DeliverCallback
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeoutOrNull
import org.junit.Test
import org.testcontainers.containers.RabbitMQContainer
import org.testng.annotations.BeforeTest
import ru.vadim.edu.kotlin.back.api.v1.models.*
import ru.vadim.edu.kotlin.back.rabbit.config.RabbitConfig
import ru.vadim.edu.kotlin.back.rabbit.config.RabbitExchangeConfiguration
import ru.vadim.edu.kotlin.back.rabbit.controller.RabbitController
import ru.vadim.edu.kotlin.back.rabbit.processor.RabbitDirectProcessorV1
import kotlin.test.assertEquals

internal class RabbitMQTest {
    companion object {
        const val exchange = "test-exchange"
        const val exchangeType = "direct"
    }
    val container by lazy {
//            Этот образ предназначен для дебагинга, он содержит панель управления на порту httpPort
//            RabbitMQContainer("rabbitmq:3-management").apply {
//            Этот образ минимальный и не содержит панель управления
        RabbitMQContainer("rabbitmq:latest").apply {
            withExposedPorts(5672, 15672)
            withUser("guest", "guest")
            start()
        }
    }

    val rabbitMqTestPort: Int by lazy {
        container.getMappedPort(5672)
    }
    val config by lazy {
        RabbitConfig(
            port = rabbitMqTestPort
        )
    }
    val processorV1 by lazy {
        RabbitDirectProcessorV1(
            config = config,
            processorConfig = RabbitExchangeConfiguration(
                keyIn = "in-v1",
                keyOut = "out-v1",
                exchange = exchange,
                queue = "v1-queue",
                consumerTag = "test-tag",
                exchangeType = exchangeType
            )
        )
    }
    val controller by lazy {
        RabbitController(
            processors = setOf(processorV1)
        )
    }

    @BeforeTest
    fun tearUp() {
        controller.start()
    }

    @Test
    fun adCreateTestV1() {
        val keyOut = processorV1.processorConfig.keyOut
        val keyIn = processorV1.processorConfig.keyIn
        ConnectionFactory().apply {
            host = config.host
            port = config.port
            username = "guest"
            password = "guest"
        }.newConnection().use { connection ->
            connection.createChannel().use { channel ->
                var responseJson = ""
                channel.exchangeDeclare(exchange, "direct")
                val queueOut = channel.queueDeclare().queue
                channel.queueBind(queueOut, exchange, keyOut)
                val deliverCallback = DeliverCallback { consumerTag, delivery ->
                    responseJson = String(delivery.body, Charsets.UTF_8)
                    println(" [x] Received by $consumerTag: '$responseJson'")
                }
                channel.basicConsume(queueOut, true, deliverCallback, CancelCallback { })

                channel.basicPublish(exchange, keyIn, null, apiV1mapper.writeValueAsBytes(profileCreate))

                runBlocking {
                    withTimeoutOrNull(265L) {
                        while (responseJson.isBlank()) {
                            delay(10)
                        }
                    }
                }

                println("RESPONSE: $responseJson")
                val response = apiV1mapper.readValue(responseJson, ProfileCreateResponse::class.java)
                val expected = PrProfileStub.get()

                assertEquals(expected.login, response.profile?.login)
                assertEquals(expected.firstName, response.profile?.firstName)
            }
        }
    }

    private val profileCreate = with(PrProfileStub.get()) {
        ProfileCreateRequest(
            profile = ProfileCreateObject(
                login = "user",
                firstName = "user",
                lastName = "userov",
                email = "user@user.ru",
                birthday = "1996-29-07",

            ),
            requestType = "create",
            debug = ProfileDebug(
                mode = ProfileRequestDebugMode.STUB,
                stub = ProfileRequestDebugStubs.SUCCESS
            )
        )
    }
}