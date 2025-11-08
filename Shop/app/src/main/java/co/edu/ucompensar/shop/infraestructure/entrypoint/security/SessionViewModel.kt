package co.edu.ucompensar.shop.infraestructure.entrypoint.security

import androidx.lifecycle.ViewModel
import co.edu.ucompensar.shop.domain.model.user.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SessionViewModel : ViewModel() {
    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser = _currentUser.asStateFlow()

    fun onLoginSuccess(user: User) {
        _currentUser.update { user }
    }

    fun onLogout() {
        _currentUser.update { null }
    }
}