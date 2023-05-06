package ru.vadim.edu.kotlin.back.v1

import PrContext
import fromTransport
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import ru.vadim.edu.kotlin.back.api.v1.models.*
import toTransportCreate
import toTransportDelete
import toTransportRead
import toTransportSearch
import toTransportUpdate

suspend fun ApplicationCall.createProfile() {
    val request = receive<ProfileCreateRequest>()
    val context = PrContext()
    context.fromTransport(request)
    context.prResponse  = PrProfileStub.get()
    respond(context.toTransportCreate())
}

suspend fun ApplicationCall.readProfile() {
    val request = receive<ProfileReadRequest>()
    val context = PrContext()
    context.fromTransport(request)
    context.prResponse = PrProfileStub.get()
    respond(context.toTransportRead())
}

suspend fun ApplicationCall.updateProfile() {
    val request = receive<ProfileUpdateRequest>()
    val context = PrContext()
    context.fromTransport(request)
    context.prResponse = PrProfileStub.get()
    respond(context.toTransportUpdate())
}

suspend fun ApplicationCall.deleteProfile() {
    val request = receive<ProfileDeleteRequest>()
    val context = PrContext()
    context.fromTransport(request)
    context.prResponse = PrProfileStub.get()
    respond(context.toTransportDelete())
}

suspend fun ApplicationCall.searchProfile() {
    val request = receive<ProfileSearchRequest>()
    val context = PrContext()
    context.fromTransport(request)
    context.prResponse = PrProfileStub.get()
    respond(context.toTransportSearch())
}