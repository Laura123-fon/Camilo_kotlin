package com.example.app_definida.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.app_definida.data.local.UserManager

class UsuarioViewModelFactory(private val userManager: UserManager) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UsuarioViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UsuarioViewModel(userManager) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
