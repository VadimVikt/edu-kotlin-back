package ru.vadim.edu.kotlin.back.v1

import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Route.v1Profile() {
    route("profile") {
        post("create"){
            call.createProfile()
        }
        post("read") {
            call.readProfile()
        }
        post("update") {
            call.updateProfile()
        }
        post("delete") {
            call.deleteProfile()
        }
        post("search") {
            call.searchProfile()
        }
    }
}