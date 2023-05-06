import models.PrProfile
import models.PrProfileId

object PrProfileUser {

    val PROFILE_STUB: PrProfile
        get() = PrProfile(
            id = PrProfileId("12345"),
            login = "UserLogin",
            firstName = "User",
            lastName = "Userov",
            email = "user@user.ru",
            birthday = "1996-07-29"
        )
}