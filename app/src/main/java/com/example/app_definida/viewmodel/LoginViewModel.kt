package com.example.app_definida.viewmodel

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
                userPreferencesRepository.guardarUserId(user.id.toString())
                userPreferencesRepository.guardarEstadoSesion(true)
                _loginState.value = LoginState.Success(user)

            }
        }
    }
   suspend fun register(user: User) {
        userRepository.insertUser(user)
    }
}

sealed class LoginState {
    object Idle : LoginState()
    data class Success(val user: User) : LoginState() // Debe ser una data class con el usuario
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
