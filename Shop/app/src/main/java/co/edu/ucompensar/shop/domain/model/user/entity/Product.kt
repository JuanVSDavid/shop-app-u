package co.edu.ucompensar.shop.domain.model.user.entity

data class Product(
    var id: Long,
    var name: String,
    var description: String,
    var price: Double,
    var stock: Int,
    var image: String,
    var userId: Long
)