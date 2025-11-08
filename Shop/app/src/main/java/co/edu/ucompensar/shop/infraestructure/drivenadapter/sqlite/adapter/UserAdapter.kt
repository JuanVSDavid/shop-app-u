package co.edu.ucompensar.shop.infraestructure.drivenadapter.sqlite.adapter

import android.content.Context
import co.edu.ucompensar.shop.domain.model.user.User
import co.edu.ucompensar.shop.domain.model.user.gateways.UserRepository
import co.edu.ucompensar.shop.infraestructure.drivenadapter.sqlite.config.AppDatabase
import co.edu.ucompensar.shop.infraestructure.drivenadapter.sqlite.entity.UserEntity
import co.edu.ucompensar.shop.infraestructure.drivenadapter.sqlite.repository.UserDao

class UserAdapter(context: Context) : UserRepository{
    private val userDao: UserDao

    init {
        val database = AppDatabase.getDatabase(context.applicationContext)
        this.userDao = database.userDao()
    }

    override suspend fun createUser(user: User): User {
        val userEntity = user.toEntity()
        val newId = userDao.insertUser(userEntity)
        return user.copy(id = newId)
    }

    override suspend fun findUserByEmail(email: String): User? {
        val userEntity = userDao.getUserByEmail(email)
        return userEntity?.toDomainModel()
    }

    private fun UserEntity.toDomainModel(): User {
        return User(
            id = this.id,
            firstName = this.firstName,
            email = this.email,
            pass = this.pass,
            lastName = this.lastName
        )
    }

    private fun User.toEntity(): UserEntity {
        return UserEntity(
            id = if (this.id == 0L) 0 else this.id,
            firstName = this.firstName,
            email = this.email,
            pass = this.pass,
            lastName = this.lastName
        )
    }

}