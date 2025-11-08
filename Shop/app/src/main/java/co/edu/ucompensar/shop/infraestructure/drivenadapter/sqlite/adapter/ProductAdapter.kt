package co.edu.ucompensar.shop.infraestructure.drivenadapter.sqlite.adapter

import android.content.Context
import co.edu.ucompensar.shop.domain.model.user.entity.Product
import co.edu.ucompensar.shop.domain.model.user.gateways.ProductRepository
import co.edu.ucompensar.shop.infraestructure.drivenadapter.sqlite.config.AppDatabase
import co.edu.ucompensar.shop.infraestructure.drivenadapter.sqlite.entity.ProductEntity
import co.edu.ucompensar.shop.infraestructure.drivenadapter.sqlite.repository.ProductDao

class ProductAdapter(context: Context) : ProductRepository{
    private val productDao: ProductDao

    init {
        val database = AppDatabase.getDatabase(context.applicationContext)
        this.productDao = database.productDao()
    }

    override suspend fun getAllProducts(): List<Product> {
        val productEntities = productDao.getAllProducts()
        return productEntities.map { it.toDomainModel() }
    }

    override suspend fun getAllProductsByUser(id: Long): List<Product> {
        val productEntities = productDao.getProductsByUserId(id)
        return productEntities.map { it.toDomainModel() }
    }

    override suspend fun createProduct(
        product: Product
    ): Product {
        val productEntity = product.toEntity()
        val newId = productDao.insertProduct(productEntity)
        return product.copy(id = newId)
    }

    override suspend fun updateProduct(
        product: Product
    ): Product {
        val productEntity = product.toEntity()
        productDao.updateProduct(productEntity)
        return product
    }

    override suspend fun deleteProduct(
        id: Long
    ) {
        productDao.deleteProduct(productDao.getProductById(id))
    }

    override suspend fun findProductById(id: Long): Product? {
        return productDao.getProductById(id).toDomainModel()
    }

    private fun ProductEntity.toDomainModel(): Product {
        return Product(
            id = this.id,
            name = this.name,
            description = this.description,
            price = this.price,
            stock = this.stock,
            image = this.image,
            userId = this.userId
        )
    }
    private fun Product.toEntity(): ProductEntity {
        return ProductEntity(
            id = if (this.id.toInt() == 0) 0 else this.id,
            name = this.name,
            description = this.description,
            price = this.price,
            stock = this.stock,
            image = this.image,
            userId = this.userId
        )
    }
}