package co.edu.ucompensar.shop.domain.usecase

import android.content.Context
import android.util.Patterns
import co.edu.ucompensar.shop.domain.model.user.User
import co.edu.ucompensar.shop.domain.model.user.gateways.UserRepository
import co.edu.ucompensar.shop.infraestructure.drivenadapter.sqlite.adapter.UserAdapter

class UserUseCase (private val context: Context){
    private val repository: UserRepository

    init {
        this.repository = UserAdapter(context)
    }

    suspend fun login(email: String, pass: String): User {
        if (email.trim().isBlank() || pass.trim().isBlank()) {
            throw IllegalArgumentException("El correo y la contraseña no pueden estar vacíos.")
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email.trim()).matches()) {
            throw IllegalArgumentException("El formato del correo electrónico no es válido.")
        }

        if (pass.length < 6) {
            throw IllegalArgumentException("La contraseña debe tener al menos 6 caracteres.")
        }

        val user = repository.findUserByEmail(email.trim())
            ?: throw IllegalArgumentException("El correo o la contraseña son incorrectos.")

        if (user.pass != pass) {
            throw IllegalArgumentException("El correo o la contraseña son incorrectos.")
        }
        return user
    }

    suspend fun register(user: User, confirmPass: String): User {
        if (user.email.trim().isBlank() || user.pass.trim().isBlank()) {
            throw IllegalArgumentException("El correo y la contraseña no pueden estar vacíos.")
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(user.email.trim()).matches()) {
            throw IllegalArgumentException("El formato del correo electrónico no es válido.")
        }
        if (user.pass.length < 6) {
            throw IllegalArgumentException("La contraseña debe tener al menos 6 caracteres.")
        }
        if (user.pass != confirmPass) {
            throw IllegalArgumentException("Las contraseñas no coinciden.")
        }
        val existingUser = repository.findUserByEmail(user.email.trim())
        if (existingUser != null) {
            throw IllegalArgumentException("El correo electrónico es invalido.")
        }
        return repository.createUser(user)
    }
}