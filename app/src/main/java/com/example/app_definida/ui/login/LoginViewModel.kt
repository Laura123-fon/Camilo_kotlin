package com.example.app_definida.ui.login

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.app_definida.database.AppDatabase
import com.example.app_definida.model.User
import com.example.app_definida.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class LoginViewModel(application: Application) : ViewModel() {

    private val userRepository: UserRepository

    init {
        val userDao = AppDatabase.getDatabase(application).userDao()
        userRepository = UserRepository(userDao)
    }

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState.asStateFlow()

    fun login(email: String, password: String) {
        viewModelScope.launch {
            // Usamos firstOrNull para obtener solo un resultado y evitar que el Flow se quede escuchando
            val user = userRepository.getUserByEmail(email).firstOrNull()
            if (user == null) {
                _loginState.value = LoginState.Error("El correo electrónico no está registrado.")
            } else if (user.passwordHash != password) {
                _loginState.value = LoginState.Error("La contraseña es incorrecta.")
            } else {
                _loginState.value = LoginState.Success
            }
        }
    }

    // Convertida a una función de suspensión para asegurar que la operación de escritura se complete
    // antes de continuar con otro proceso (como la navegación).
    suspend fun register(user: User) {
        userRepository.insertUser(user)
    }
}

sealed class LoginState {
    object Idle : LoginState()
    object Success : LoginState()
    data class Error(val message: String) : LoginState()
}

class LoginViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LoginViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
