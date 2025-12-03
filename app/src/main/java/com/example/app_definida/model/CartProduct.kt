package com.example.app_definida.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_products")
data class CartProduct(
    @PrimaryKey
    val id: String,
    val nombre: String,
    val precio: Double,
    val imagenUrl: String,
    val cantidad: Int,
    val descuento: Double
)
