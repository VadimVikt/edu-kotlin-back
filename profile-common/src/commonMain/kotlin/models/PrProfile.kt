package models

data class PrProfile(
    var id: PrProfileId = PrProfileId.NONE,
    var login: String = "",
    var firstName: String = "",
    var lastName: String = "",
    var birthday: String = "",
    var email: String = ""
)
