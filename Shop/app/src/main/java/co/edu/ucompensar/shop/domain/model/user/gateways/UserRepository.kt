package co.edu.ucompensar.shop.domain.model.user.gateways

import co.edu.ucompensar.shop.domain.model.user.User

interface UserRepository{
    suspend fun createUser(user: User) : User
    suspend fun findUserByEmail(email: String): User?
}