package com.example.app_definida.ui.state

/**
 * Describe la estructura de los posibles errores en el formulario.
 * Cada campo es un String opcional que contendrá el mensaje de error si lo hay.
 */
data class FormErrors(
    val nombre: String? = null,
    val correo: String? = null,
    val clave: String? = null,
    val direccion: String? = null
)

/**
 * Representa el estado completo de la pantalla de registro en un momento dado.
 * Contiene todos los datos que el usuario ha introducido y los errores actuales.
 */
data class RegistroUiState(
    val nombre: String = "",
    val correo: String = "",
    val clave: String = "",
    val direccion: String = "",
    val aceptaTerminos: Boolean = false,
    val errores: FormErrors = FormErrors()
)
