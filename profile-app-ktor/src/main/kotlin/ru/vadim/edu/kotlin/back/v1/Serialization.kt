package ru.vadim.edu.kotlin.back.v1

import apiV1mapper
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.serialization.jackson.*
import io.ktor.server.response.*
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureSerialization() {
    install(ContentNegotiation) {
        jackson{
            setConfig(apiV1mapper.serializationConfig)
            setConfig(apiV1mapper.deserializationConfig)
        }
    }
    routing {
        get("/json/kotlinx-serialization") {
                call.respond(mapOf("hello" to "world"))
            }
    }
}