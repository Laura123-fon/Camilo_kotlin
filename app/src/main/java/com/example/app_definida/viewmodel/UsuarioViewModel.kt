package com.example.app_definida.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_definida.data.UserManager
import com.example.app_definida.data.UserProfile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UsuarioViewModel(private val userManager: UserManager) : ViewModel() {

    private val _userProfile = MutableStateFlow<UserProfile?>(null)
    val userProfile = _userProfile.asStateFlow()

    init {
        loadUserProfile()
    }

    // ¡SOLUCIÓN! Se quita el `private` para que la función sea pública
    fun loadUserProfile() {
        viewModelScope.launch {
            _userProfile.value = userManager.getUserProfile()
            print(_userProfile)
        }
    }
}
