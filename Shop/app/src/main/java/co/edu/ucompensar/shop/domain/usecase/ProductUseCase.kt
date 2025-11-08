package co.edu.ucompensar.shop.domain.usecase

import android.content.Context
import android.net.Uri
import co.edu.ucompensar.shop.domain.model.user.entity.Product
import co.edu.ucompensar.shop.domain.model.user.gateways.ProductRepository
import co.edu.ucompensar.shop.domain.model.user.User
import co.edu.ucompensar.shop.infraestructure.drivenadapter.sqlite.adapter.ProductAdapter
import java.io.File
import java.io.FileOutputStream
import java.util.UUID

class ProductUseCase(private val context: Context){
    private val repository: ProductRepository

    init {
        this.repository = ProductAdapter(context)
    }

    fun saveImageToInternalStorage(uri: Uri): Uri? {
        return try {
            val inputStream = context.contentResolver.openInputStream(uri)

            val fileName = "IMG_${UUID.randomUUID()}.jpg"
            val file = File(context.filesDir, fileName)

            val outputStream = FileOutputStream(file)

            inputStream?.copyTo(outputStream)

            inputStream?.close()
            outputStream.close()

            Uri.fromFile(file)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun validateProduct(product: Product) {
        if (product.name.trim().isBlank()) {
            throw IllegalArgumentException("El nombre del producto no puede estar vacío.")
        }
        if (product.price <= 0.0) {
            throw IllegalArgumentException("El precio del producto debe ser mayor que cero.")
        }
        if (product.stock < 0) {
            throw IllegalArgumentException("El stock no puede ser negativo.")
        }
        if (product.userId <= 0) {
            throw IllegalArgumentException("El ID del usuario no es válido.")
        }
        if (product.description.trim().isBlank()) {
            throw IllegalArgumentException("La descripción del producto no puede estar vacía.")
        }
        if (product.image.trim().isBlank()) {
            throw IllegalArgumentException("La imagen del producto no puede estar vacía.")
        }
    }

    private fun authorizeUserAction(productUserId: Long, currentUserId: Long) {
        if (currentUserId != productUserId) {
            throw IllegalArgumentException("No tienes permiso para modificar este producto.")
        }
    }

    suspend fun createProduct(product: Product, user: User): Product {
        authorizeUserAction(product.userId, user.id)
        validateProduct(product)
        return repository.createProduct(product);
    }

    suspend fun updateProduct(product: Product, user: User): Product {
        authorizeUserAction(product.userId, user.id)
        validateProduct(product)
        return repository.updateProduct(product);
    }

    suspend fun deleteProduct(id: Long, user: User){
        val product = repository.findProductById(id)
            ?: throw IllegalArgumentException("El producto que intentas eliminar no existe.")

        authorizeUserAction(product.userId, user.id)
        return repository.deleteProduct(id)
    }

    suspend fun getAllProductsByUser(user: User): List<Product>{
        return repository.getAllProductsByUser(user.id)
    }

    suspend fun getAllProducts(): List<Product>{
        return repository.getAllProducts()
    }

}