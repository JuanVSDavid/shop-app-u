package co.edu.ucompensar.shop.infraestructure.drivenadapter.sqlite.repository

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import co.edu.ucompensar.shop.infraestructure.drivenadapter.sqlite.entity.ProductEntity

@Dao
interface ProductDao {
    @Insert
    suspend fun insertProduct(product: ProductEntity) : Long

    @Update
    suspend fun updateProduct(product: ProductEntity)

    @Delete
    suspend fun deleteProduct(product: ProductEntity)

    @Query("SELECT * FROM products WHERE userId = :userId ORDER BY id DESC")
    suspend fun getProductsByUserId(userId: Long): List<ProductEntity>

    @Query("SELECT * FROM products ORDER BY id DESC")
    suspend fun getAllProducts(): List<ProductEntity>

    @Query("SELECT * FROM products WHERE id = :id ORDER BY id DESC")
    suspend fun getProductById(id: Long): ProductEntity

}