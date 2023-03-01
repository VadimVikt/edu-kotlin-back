import ru.vadim.edu.kotlin.back.api.v1.models.IResponse
import ru.vadim.edu.kotlin.back.api.v1.models.ProfileCreateResponse
import ru.vadim.edu.kotlin.back.api.v1.models.ProfileResponseObject
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class ResponseSerializationTest {
    private val response = ProfileCreateResponse(
        requestId = "123",
        profile = ProfileResponseObject(
            login = "user",
            firstName = "User",
            lastName = "Userov",
            birthday = "1980-01-01",
            email = "user@userov.ru"
        )
    )

    @Test
    fun serialize() {
        val json = apiV1mapper.writeValueAsString(response)
        assertContains(json, Regex("\"login\":\\s*\"user\""))
        assertContains(json, Regex("\"firstName\":\\s*\"User\""))
        assertContains(json, Regex("\"lastName\":\\s*\"Userov\""))
        assertContains(json, Regex("\"birthday\":\\s*\"1980-01-01\""))
        assertContains(json, Regex("\"email\":\\s*\"user@userov.ru\""))
    }

    @Test
    fun deserialize() {
        val json = apiV1mapper.writeValueAsString(response)
        val obj = apiV1mapper.readValue(json, IResponse::class.java) as ProfileCreateResponse

        assertEquals(response, obj)
    }
}