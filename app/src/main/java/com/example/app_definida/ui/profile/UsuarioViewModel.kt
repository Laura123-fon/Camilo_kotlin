package com.example.app_definida.ui.profile

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_definida.data.local.UserManager
import com.example.app_definida.data.model.UserProfile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UsuarioViewModel(private val userManager: UserManager) : ViewModel() {

    private val _userProfile = MutableStateFlow<UserProfile?>(null)
    val userProfile = _userProfile.asStateFlow()

    private val _uriImagen = MutableStateFlow<Uri?>(null)
    val uriImagen = _uriImagen.asStateFlow()

    init {
        loadUserProfile()
    }

    private fun loadUserProfile() {
        viewModelScope.launch {
            _userProfile.value = userManager.getUserProfile()
        }
    }

    fun actualizarUriImagen(uri: Uri?) {
        _uriImagen.update { uri }
    }
}