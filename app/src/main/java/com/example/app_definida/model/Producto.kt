package com.example.app_definida.model

data class Producto(
    val id: String,
    val nombre: String,
    val descripcion: String,
    val precio: Double,
    val categoria: String,
    val imagenUrl: String // Usaremos una URL para la imagen
)
