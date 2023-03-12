import exceptions.UnknownRequestValue
import models.*
import ru.vadim.edu.kotlin.back.api.v1.models.*
import stubs.PrStub


fun PrContext.fromTransport(request: IRequest) = when(request) {
    is ProfileCreateRequest -> fromTransport(request)
    is ProfileDeleteRequest -> fromTransport(request)
    is ProfileReadRequest -> fromTransport(request)
    is ProfileUpdateRequest -> fromTransport(request)
    is ProfileSearchRequest -> fromTransport(request)
    else -> throw UnknownRequestValue(request::class)
}

fun IRequest?.requestId() = this?.requestId?.let { PrRequestId(it) } ?: PrRequestId.NONE
fun String?.toProfileId() = this?.let {PrProfileId(it)} ?: PrProfileId.NONE
fun String?.toProfileWithId() = PrProfile(id = this.toProfileId())

fun ProfileCreateObject.toInternal(): PrProfile = PrProfile(
    login = this.login ?: "",
    firstName = this.firstName ?: "",
    lastName = this.lastName ?: "",
    birthday = this.birthday ?: "",
    email = this.email ?: ""
)

fun ProfileUpdateObject.toInternal(): PrProfile = PrProfile(
    id = this.id.toProfileId(),
    login = this.login ?: "",
    firstName = this.firstName ?: "",
    lastName = this.lastName ?: "",
    birthday = this.birthday ?: "",
    email = this.email ?: ""
)

fun ProfileSearchFilter?.toInternal(): PrFilterRequest = PrFilterRequest(
    searchString = this?.searchString ?: ""
)

fun PrFilterRequest.toInternal() = PrFilterRequest(
    searchString = this?.searchString ?: ""
)

fun PrContext.fromTransport(request: ProfileCreateRequest) {
    command = PrCommand.CREATE
    requestId = request.requestId()
    prRequest = request.profile?.toInternal() ?: PrProfile()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun PrContext.fromTransport(request: ProfileReadRequest) {
    command = PrCommand.READ
    requestId = request.requestId()
    prRequest = request.profile?.id.toProfileWithId()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun PrContext.fromTransport(request: ProfileDeleteRequest) {
    command = PrCommand.DELETE
    requestId = request.requestId()
    prRequest = request.profile?.id.toProfileWithId()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun PrContext.fromTransport(request: ProfileUpdateRequest) {
    command = PrCommand.UPDATE
    requestId = request.requestId()
    prRequest = request.profile?.toInternal() ?: PrProfile()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun PrContext.fromTransport(request: ProfileSearchRequest) {
    command = PrCommand.SEARCH
    requestId = request.requestId()
    prFilterRequest = request.profileFilter.toInternal()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun ProfileDebug?.transportToWorkMode() = when(this?.mode) {
    ProfileRequestDebugMode.PROD -> PrWorkMode.PROD
    ProfileRequestDebugMode.TEST -> PrWorkMode.TEST
    ProfileRequestDebugMode.STUB -> PrWorkMode.STUB
    null -> PrWorkMode.PROD
}

fun ProfileDebug?.transportToStubCase() = when(this?.stub) {
    ProfileRequestDebugStubs.SUCCESS -> PrStub.SUCCESS
    ProfileRequestDebugStubs.NOT_FOUND -> PrStub.NOT_FOUND
    ProfileRequestDebugStubs.BAD_ID -> PrStub.BAD_ID
    ProfileRequestDebugStubs.BAD_LOGIN -> PrStub.BAD_LOGIN
    ProfileRequestDebugStubs.BAD_FIRST_NAME -> PrStub.BAD_FIRST_NAME
    ProfileRequestDebugStubs.BAD_LAST_NAME -> PrStub.BAD_LAST_NAME
    ProfileRequestDebugStubs.BAD_BIRTHDAY -> PrStub.BAD_BIRTHDAY
    ProfileRequestDebugStubs.BAD_EMAIL -> PrStub.BAD_EMAIL
    ProfileRequestDebugStubs.CANNOT_DELETE -> PrStub.CANNOT_DELETE
    ProfileRequestDebugStubs.BAD_SEARCH_STRING -> PrStub.BAD_SEARCH_STRING
    null -> PrStub.NONE
}