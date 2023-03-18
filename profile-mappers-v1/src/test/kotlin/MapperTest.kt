import models.*
import ru.vadim.edu.kotlin.back.api.v1.models.*
import stubs.PrStub
import kotlin.test.Test
import kotlin.test.assertEquals

class MapperTest {
    @Test
    fun fromTransport() {
        val req = ProfileCreateRequest(
            requestId = "123",
            debug = ProfileDebug(
                mode = ProfileRequestDebugMode.STUB,
                stub = ProfileRequestDebugStubs.SUCCESS
            ),
            profile = ProfileCreateObject(
                login = "user001",
                firstName = "User",
                lastName = "User",
                birthday = "1970-01-01",
                email = "user@user.ru"
            )
        )
        val context = PrContext()
        context.fromTransport(req)

        assertEquals(PrStub.SUCCESS, context.stubCase)
        assertEquals(PrWorkMode.STUB, context.workMode)
        assertEquals("user001", context.prRequest.login)
        assertEquals("User", context.prRequest.firstName)
        assertEquals("User", context.prRequest.lastName)
        assertEquals("1970-01-01", context.prRequest.birthday)
        assertEquals("user@user.ru", context.prRequest.email)
    }

    @Test
    fun toTransport() {
        val context = PrContext(
            requestId = PrRequestId("12345"),
            command = PrCommand.CREATE,
            prResponse = PrProfile(
                login = "user001",
                firstName = "User",
                lastName = "User",
                birthday = "1970-01-01",
                email = "user@user.ru"
            ),
            errors = mutableListOf(
                PrError(
                    code = "err",
                    group = "request",
                    field = "login",
                    message = "wrong login"
                )
            ),
            state = PrState.RUNNING
        )

        val req = context.toTransport() as ProfileCreateResponse

        assertEquals("12345", req.requestId)
        assertEquals("user001", req.profile?.login)
        assertEquals("User", req.profile?.firstName)
        assertEquals(1, req.errors?.size)
        assertEquals("err", req.errors?.firstOrNull()?.code)
        assertEquals("request", req.errors?.firstOrNull()?.group)
        assertEquals("login", req.errors?.firstOrNull()?.field)
        assertEquals("wrong login", req.errors?.firstOrNull()?.message)

    }
}