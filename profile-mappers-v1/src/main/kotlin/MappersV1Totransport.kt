import exceptions.UnknownProfileCommand
import models.*
import ru.vadim.edu.kotlin.back.api.v1.models.*

fun PrContext.toTransport(): IResponse = when(val cmd = command) {
    PrCommand.CREATE -> toTransportCreate()
    PrCommand.READ -> toTransportRead()
    PrCommand.UPDATE -> toTransportUpdate()
    PrCommand.DELETE -> toTransportDelete()
    PrCommand.SEARCH -> toTransportSearch()
    PrCommand.NONE -> throw UnknownProfileCommand(cmd)
}

fun PrContext.toTransportCreate() = ProfileCreateResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == PrState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    profile = prResponse.toTransportPr()
)

fun PrContext.toTransportRead() = ProfileReadResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == PrState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    profile = prResponse.toTransportPr()
)

fun PrContext.toTransportUpdate() = ProfileUpdateResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == PrState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    profile = prResponse.toTransportPr()
)

fun PrContext.toTransportDelete() = ProfileDeleteResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == PrState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    profile = prResponse.toTransportPr()
)

fun PrContext.toTransportSearch() = ProfileSearchResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == PrState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    profile = prResponse.toTransportPr()
)

private fun PrProfile.toTransportPr(): ProfileResponseObject = ProfileResponseObject(
    id = id.takeIf { it != PrProfileId.NONE }?.asString(),
    login = login.takeIf { it.isNotBlank() },
    firstName = firstName.takeIf { it.isNotBlank() },
    lastName = lastName.takeIf { it.isNotBlank() },
    birthday = birthday.takeIf { it.isNotBlank() },
    email = email.takeIf { it.isNotBlank() },
)

private fun List<PrError>.toTransportErrors(): List<Error>? = this
    .map { it.toTransportPr() }
    .toList()
    .takeIf { it.isNotEmpty() }

private fun PrError.toTransportPr() = Error(
    code = code.takeIf { it.isNotBlank() },
    group = group.takeIf { it.isNotBlank() },
    field = field.takeIf { it.isNotBlank() },
    message = message.takeIf { it.isNotBlank() },
)