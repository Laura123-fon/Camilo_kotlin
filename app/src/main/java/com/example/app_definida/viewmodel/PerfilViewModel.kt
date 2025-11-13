package com.example.app_definida.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class PerfilViewModel : ViewModel() {

    private val _uriImagen = MutableStateFlow<Uri?>(null)
    val uriImagen = _uriImagen.asStateFlow()

    fun actualizarUriImagen(uri: Uri?) {
        _uriImagen.update { uri }
    }
}