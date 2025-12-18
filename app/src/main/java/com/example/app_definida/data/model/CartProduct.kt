package com.example.app_definida.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_products")
data class CartProduct(
    @PrimaryKey
    val id: Long,
    val nombre: String,
    val precio: Double,
    val imagenUrl: String,
    val cantidad: Int,
    val descuento: Double,
    val descripcion: String,
    val origen: String,
    val sostenibilidad: String,
    val categoria: String,
    val receta: String,
    val calificacion: Double,
    val stock: Int
)
