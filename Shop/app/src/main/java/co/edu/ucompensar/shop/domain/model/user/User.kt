package co.edu.ucompensar.shop.domain.model.user

data class User(
    var id: Long,
    var firstName: String,
    var lastName: String,
    var email: String,
    var pass: String,
)