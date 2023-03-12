import ru.vadim.edu.kotlin.back.api.v1.models.*
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class RequestSerializationTest {
    private val request = ProfileCreateRequest(
        requestId = "12345",
        debug = ProfileDebug(
            mode = ProfileRequestDebugMode.STUB,
            stub = ProfileRequestDebugStubs.BAD_LOGIN
        ),
        profile = ProfileCreateObject(
            login = "user-01",
            firstName = "User",
            lastName = "Userov",
            birthday = "1980-01-01",
            email = "user@userov.ru"
        )
    )
    @Test
    fun serialize() {
        val json = apiV1mapper.writeValueAsString(request)
        assertContains(json, Regex("\"login\":\\s*\"user-01\""))
        assertContains(json, Regex("\"firstName\":\\s*\"User\""))
        assertContains(json, Regex("\"lastName\":\\s*\"Userov\""))
        assertContains(json, Regex("\"birthday\":\\s*\"1980-01-01\""))
        assertContains(json, Regex("\"email\":\\s*\"user@userov.ru\""))
    }

    @Test
    fun deserialize() {
        val json = apiV1mapper.writeValueAsString(request)
        val obj = apiV1mapper.readValue(json, IRequest::class.java) as ProfileCreateRequest
        assertEquals(request, obj)
    }
}