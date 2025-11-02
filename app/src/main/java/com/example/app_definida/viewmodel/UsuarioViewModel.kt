package com.example.app_definida.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

// Datos para los errores del formulario (puedes dejarlos vacíos)
data class FormErrors(
    val nombre: String? = null,
    val correo: String? = null,
    val clave: String? = null,
    val direccion: String? = null
)

// Estado del formulario de registro
data class RegistroUiState(
    val nombre: String = "",
    val correo: String = "",
    val clave: String = "",
    val direccion: String = "",
    val aceptaTerminos: Boolean = false,
    val errores: FormErrors = FormErrors()
)

class UsuarioViewModel : ViewModel() {

    private val _estado = MutableStateFlow(RegistroUiState())
    val estado = _estado.asStateFlow()

    fun onNombreChange(nombre: String) {
        _estado.update { it.copy(nombre = nombre) }
    }

    fun onCorreoChange(correo: String) {
        _estado.update { it.copy(correo = correo) }
    }

    fun onClaveChange(clave: String) {
        _estado.update { it.copy(clave = clave) }
    }

    fun onDireccionChange(direccion: String) {
        _estado.update { it.copy(direccion = direccion) }
    }

    fun onAceptarTerminosChange(acepta: Boolean) {
        _estado.update { it.copy(aceptaTerminos = acepta) }
    }

    fun validarFormulario(): Boolean {
        // Por ahora, simplemente retornamos true para que la app compile y navegue.
        // Aquí puedes añadir tu lógica de validación más tarde.
        return _estado.value.aceptaTerminos
    }
}
