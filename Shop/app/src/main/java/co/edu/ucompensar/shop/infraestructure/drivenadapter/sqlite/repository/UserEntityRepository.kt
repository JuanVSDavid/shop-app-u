package co.edu.ucompensar.shop.infraestructure.drivenadapter.sqlite.repository

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import co.edu.ucompensar.shop.infraestructure.drivenadapter.sqlite.entity.UserEntity

@Dao
interface UserDao {
    @Insert
    suspend fun insertUser(user: UserEntity): Long

    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    suspend fun getUserByEmail(email: String): UserEntity?

    @Query("SELECT * FROM users WHERE id = :id LIMIT 1")
    suspend fun getUserById(id: Long): UserEntity?
}