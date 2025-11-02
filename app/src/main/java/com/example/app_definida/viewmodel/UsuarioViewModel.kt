package com.example.app_definida.viewmodel

import androidx.lifecycle.ViewModel
import com.example.app_definida.ui.screens.UsuarioErrores
import com.example.app_definida.ui.screens.UsuarioUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class UsuarioViewModel : ViewModel() {
    private val _estado = MutableStateFlow(UsuarioUiState())
    val estado: StateFlow<UsuarioUiState> = _estado.asStateFlow()

    fun onNombreChange(nombre: String) {
        _estado.update { it.copy(nombre = nombre, errores = it.errores.copy(nombre = null)) }
    }

    fun onCorreoChange(correo: String) {
        _estado.update { it.copy(correo = correo, errores = it.errores.copy(correo = null)) }
    }

    fun onClaveChange(clave: String) {
        _estado.update { it.copy(clave = clave, errores = it.errores.copy(clave = null)) }
    }

    fun onDireccionChange(direccion: String) {
        _estado.update {
            it.copy(
                direccion = direccion,
                errores = it.errores.copy(direccion = null)
            )
        }
    }

    fun onAceptarTerminosChange(acepta: Boolean) {
        _estado.update { it.copy(aceptaTerminos = acepta) }
    }

    // En UsuarioViewModel.kt, dentro de la clase

    fun validarFormulario(): Boolean {
        val estadoActual = _estado.value
        val nombreEsValido = estadoActual.nombre.isNotBlank()
        val correoEsValido = android.util.Patterns.EMAIL_ADDRESS.matcher(estadoActual.correo).matches()
        val claveEsValida = estadoActual.clave.length >= 6
        val aceptaTerminosEsValido = estadoActual.aceptaTerminos

        // Actualiza el estado con los errores
        _estado.update {
            it.copy(
                // CORRECCIÓN: Usa el nombre de la clase que importaste -> "UsuarioErrores"
                errores = UsuarioErrores(
                    nombre = if (nombreEsValido) null else "El nombre no puede estar vacío",
                    correo = if (correoEsValido) null else "El correo no es válido",
                    clave = if (claveEsValida) null else "La clave debe tener al menos 6 caracteres"
                    // El error de dirección no se valida aquí, así que no se añade
                )
            )
        }
        return nombreEsValido && correoEsValido && claveEsValida && aceptaTerminosEsValido
    }

}