package com.example.app_definida.data.model

data class Product(
    val id: Long,
    val nombre: String,
    val descripcion: String,
    val precio: Double,
    val stock: Int,
    val imagenUrl: String,
    val origen: String,
    val sostenibilidad: String,
    val categoria: String,
    val receta: String,
    val calificacion: Double,
    val descuento: Double = 0.0
)
