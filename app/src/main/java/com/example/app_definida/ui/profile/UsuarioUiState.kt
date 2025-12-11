package com.example.app_definida.ui.profile

data class UsuarioUiState(
    val nombre: String = "",
    val correo: String = "",
    val clave: String = "",
    val direccion: String = "",
    val aceptaTerminos: Boolean = false,
    val errores: Errores = Errores()
) {
    data class Errores(
        val nombre: String? = null,
        val correo: String? = null,
        val clave: String? = null,
        val direccion: String? = null
    )
}
