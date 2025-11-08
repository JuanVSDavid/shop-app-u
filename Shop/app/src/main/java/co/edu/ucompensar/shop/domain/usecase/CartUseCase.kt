package co.edu.ucompensar.shop.domain.usecase

import co.edu.ucompensar.shop.domain.model.user.entity.Product
import co.edu.ucompensar.shop.domain.model.user.value.CartItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

object CartUseCase {
    private val _cartItems = MutableStateFlow<Map<Long, CartItem>>(emptyMap())
    val cartItems = _cartItems.asStateFlow()

    fun addProduct(product: Product) {
        _cartItems.update { currentCart ->
            val cart = currentCart.toMutableMap()
            val existingItem = cart[product.id]

            if (existingItem != null) {
                if (existingItem.quantity < product.stock) {
                    cart[product.id] = existingItem.copy(quantity = existingItem.quantity + 1)
                } else {
                    throw IllegalArgumentException("No hay más stock disponible para este producto.")
                }
            } else {
                if (product.stock > 0) {
                    cart[product.id] = CartItem(product = product, quantity = 1)
                }
            }
            cart
        }
    }

    fun removeProduct(productId: Long) {
        _cartItems.update { currentCart ->
            currentCart - productId
        }
    }

    fun updateQuantity(productId: Long, newQuantity: Int) {
        _cartItems.update { currentCart ->
            val cart = currentCart.toMutableMap()
            val item = cart[productId]
            if (item != null) {
                when {
                    newQuantity <= 0 -> cart.remove(productId)
                    newQuantity <= item.product.stock -> cart[productId] = item.copy(quantity = newQuantity)
                }
            }
            cart
        }
    }

    fun clearCart() {
        _cartItems.value = emptyMap()
    }
}