package com.example.app_definida.model

import com.google.gson.annotations.SerializedName

data class Product(
    @SerializedName("id")
    val id: Long,

    @SerializedName("nombre")
    val nombre: String,

    @SerializedName("descripcion")
    val descripcion: String,

    @SerializedName("precio")
    val precio: Double,

    @SerializedName("stock")
    val stock: Int,

    @SerializedName("imagenUrl")
    val imagenUrl: String,

    @SerializedName("origen")
    val origen: String,

    @SerializedName("sostenibilidad")
    val sostenibilidad: String,

    @SerializedName("categoria")
    val categoria: String,

    @SerializedName("receta")
    val receta: String,

    @SerializedName("calificacion")
    val calificacion: Double
)
