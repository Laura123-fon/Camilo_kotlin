package com.example.app_definida.data.model

data class Product(
    val id: String,
    val nombre: String,
    val descripcion: String,
    val precio: Double,
    val categoria: String,
    val imagenUrl: String,
    val descuento: Double
)
