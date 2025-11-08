package co.edu.ucompensar.shop.infraestructure.entrypoint.util

import androidx.lifecycle.ViewModel
import co.edu.ucompensar.shop.domain.model.user.entity.Product
import co.edu.ucompensar.shop.domain.usecase.CartUseCase

class CartViewModel : ViewModel() {
    val cartItems = CartUseCase.cartItems

    fun addProductToCart(product: Product) {
        CartUseCase.addProduct(product)
    }

    fun removeProductFromCart(productId: Long) {
        CartUseCase.removeProduct(productId)
    }

    fun updateProductQuantity(productId: Long, quantity: Int) {
        CartUseCase.updateQuantity(productId, quantity)
    }
}