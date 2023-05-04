package ru.vadim.edu.kotlin.back.v1

import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.application.*

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Hello World!")
        }
        route("api/v1") {
            v1Profile()
        }

    }
}


