package co.edu.ucompensar.shop.domain.model.user.value

import co.edu.ucompensar.shop.domain.model.user.entity.Product

data class CartItem(
    val product: Product,
    val quantity: Int
)