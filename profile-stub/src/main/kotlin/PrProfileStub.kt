import PrProfileUser.PROFILE_STUB
import models.PrProfile


object PrProfileStub {

    fun get(): PrProfile = PROFILE_STUB.copy()

    fun prepareResult(block: PrProfile.() -> Unit): PrProfile = get().apply(block)
}