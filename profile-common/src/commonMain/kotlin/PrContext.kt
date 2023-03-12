import kotlinx.datetime.Instant
import models.*
import stubs.PrStub

data class PrContext(
    var command: PrCommand = PrCommand.NONE,
    var state: PrState = PrState.NONE,
    var errors: MutableList<PrError> = mutableListOf(),

    var workMode: PrWorkMode = PrWorkMode.PROD,
    var stubCase: PrStub = PrStub.NONE,

    var requestId: PrRequestId = PrRequestId.NONE,
    var timeStart: Instant = Instant.NONE,
    var prRequest: PrProfile = PrProfile(),
    var prFilterRequest: PrFilterRequest = PrFilterRequest(),
    var prResponse: PrProfile = PrProfile(),
    var prSearchResponse: MutableList<PrProfile> = mutableListOf()
)