package co.edu.ucompensar.shop.domain.model.user.gateways

import co.edu.ucompensar.shop.domain.model.user.entity.Product

interface ProductRepository {
    suspend fun getAllProducts(): List<Product>
    suspend fun getAllProductsByUser(id: Long): List<Product>
    suspend fun createProduct(product: Product) : Product
    suspend fun updateProduct(product: Product) : Product
    suspend fun deleteProduct(id: Long)
    suspend fun findProductById(id: Long): Product?
}