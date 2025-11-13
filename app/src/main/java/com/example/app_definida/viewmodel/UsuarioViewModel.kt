package com.example.app_definida.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


data class Usuario(
    val nombre: String,
    val email: String,
    val direccion: String
)

data class FormErrors(
    val nombre: String? = null,
    val correo: String? = null,
    val clave: String? = null,
    val direccion: String? = null
)

data class RegistroUiState(
    val nombre: String = "",
    val correo: String = "",
    val clave: String = "",
    val direccion: String = "",
    val aceptaTerminos: Boolean = false,
    val fotoPerfilUri: Uri? = null,
    val errores: FormErrors = FormErrors()
)


class UsuarioViewModel : ViewModel() {

    private val _estado = MutableStateFlow(RegistroUiState())
    val estado = _estado.asStateFlow()

    private val _usuario = MutableStateFlow<Usuario?>(null)
    val usuario = _usuario.asStateFlow()

    fun onNombreChange(nombre: String) { _estado.update { it.copy(nombre = nombre) } }
    fun onCorreoChange(correo: String) { _estado.update { it.copy(correo = correo) } }
    fun onClaveChange(clave: String) { _estado.update { it.copy(clave = clave) } }
    fun onDireccionChange(direccion: String) { _estado.update { it.copy(direccion = direccion) } }
    fun onAceptarTerminosChange(acepta: Boolean) { _estado.update { it.copy(aceptaTerminos = acepta) } }
    fun onFotoPerfilChange(uri: Uri) { _estado.update { it.copy(fotoPerfilUri = uri) } }

    fun onRegistroExitoso() {
        val estadoActual = _estado.value
        _usuario.update {
            Usuario(
                nombre = estadoActual.nombre,
                email = estadoActual.correo,
                direccion = estadoActual.direccion
            )
        }
    }

    fun validarFormulario(): Boolean {
        val estadoActual = _estado.value
        val erroresNuevos = FormErrors(
            nombre = if (estadoActual.nombre.isBlank()) "El nombre es requerido" else null,
            correo = if (!android.util.Patterns.EMAIL_ADDRESS.matcher(estadoActual.correo).matches()) "El correo no es válido" else null,
            clave = if (estadoActual.clave.length < 6) "La clave debe tener al menos 6 caracteres" else null
        )
        val esValido = erroresNuevos.nombre == null && erroresNuevos.correo == null && erroresNuevos.clave == null && estadoActual.aceptaTerminos
        _estado.update { it.copy(errores = erroresNuevos) }
        return esValido
    }
}