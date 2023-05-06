package ru.vadim.edu.kotlin.back.stub

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.SerializationFeature
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.jackson.*
import io.ktor.server.testing.*
import org.junit.Test
import ru.vadim.edu.kotlin.back.api.v1.models.*
import kotlin.test.assertEquals

class ProfileV1ApiTest {
    @Test
    fun create() = testApplication {
        val client = myClient()
        val response = client.post("/api/v1/profile/create") {
            val requestObj  = ProfileCreateRequest(
                requestId = "12345",
                profile = ProfileCreateObject(
                    login = "user",
                    firstName = "user",
                    lastName = "userov",
                    birthday = "1996-07-29",
                    email = "user@userov.ru"
                ),
                debug = ProfileDebug(
                    mode = ProfileRequestDebugMode.PROD,
                    stub = ProfileRequestDebugStubs.SUCCESS
                )
            )
            contentType(ContentType.Application.Json)
            setBody(requestObj)
        }
        val responseObj = response.body<ProfileCreateResponse>()
        assertEquals(200, response.status.value)
        assertEquals("12345", responseObj.profile?.id)
    }

    @Test
    fun read() = testApplication {
        val client = myClient()
        val response = client.post("/api/v1/profile/read") {
            val requestObj = ProfileReadRequest(
                requestId = "222",
                profile = ProfileReadObject("222"),
                debug = ProfileDebug(
                    mode = ProfileRequestDebugMode.PROD,
                    stub = ProfileRequestDebugStubs.SUCCESS
                )
            )
            contentType(ContentType.Application.Json)
            setBody(requestObj)
        }
        val responseObj = response.body<ProfileReadResponse>()
        assertEquals(200, response.status.value)
        assertEquals("12345", responseObj.profile?.id)

    }

    @Test
    fun update() = testApplication {
        val client = myClient()
        val response = client.post("/api/v1/profile/update") {
            val responseObj = ProfileUpdateRequest(
                requestId = "12345",
                profile = ProfileUpdateObject(
                    id = "222",
                    login = "user",
                    firstName = "user",
                    lastName = "userov",
                    birthday = "1996-07-29",
                    email = "user@userov.ru"
                ),
                debug = ProfileDebug(
                    mode = ProfileRequestDebugMode.PROD,
                    stub = ProfileRequestDebugStubs.SUCCESS
                )
            )
            contentType(ContentType.Application.Json)
            setBody(responseObj)
        }
        val responseObj = response.body<ProfileUpdateResponse>()
        assertEquals(200, response.status.value)
        assertEquals("12345", responseObj.profile?.id)
    }

    @Test
    fun delete() = testApplication {
        val client = myClient()
        val response = client.post("/api/v1/profile/delete") {
            val responseObj = ProfileDeleteRequest(
                requestId = "123456",
                profile = ProfileDeleteObject(
                    id = "222"
                ),
                debug = ProfileDebug(
                    mode = ProfileRequestDebugMode.PROD,
                    stub = ProfileRequestDebugStubs.SUCCESS
                )
            )
            contentType(ContentType.Application.Json)
            setBody(responseObj)
        }
        val responseObj = response.body<ProfileDeleteResponse>()
        assertEquals(200, response.status.value)
        assertEquals("12345", responseObj.profile?.id)
    }

    @Test
    fun search() = testApplication {
        val client = myClient()
        val response = client.post("/api/v1/profile/search") {
            val responseObj = ProfileSearchRequest(
                requestId = "12345",
                profileFilter = ProfileSearchFilter(),
                debug = ProfileDebug(
                    mode = ProfileRequestDebugMode.PROD,
                    stub = ProfileRequestDebugStubs.SUCCESS
                )
            )
            contentType(ContentType.Application.Json)
            setBody(responseObj)
        }
        val responseObj = response.body<ProfileSearchResponse>()
        assertEquals(200, response.status.value)
        assertEquals("12345", responseObj.profile?.id)

    }

    private fun ApplicationTestBuilder.myClient() = createClient {
        install(ContentNegotiation){
            jackson{
                disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)

                enable(SerializationFeature.INDENT_OUTPUT)
                writerWithDefaultPrettyPrinter()
            }
        }
    }
}