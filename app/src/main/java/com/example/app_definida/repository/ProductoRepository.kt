package com.example.app_definida.repository

import android.R.drawable
import com.example.app_definida.R
import com.example.app_definida.model.Producto

class ProductoRepository {

    fun getProductos(): List<Producto> {
        return listOf(
            Producto(
                id = "FR001",
                nombre = "Manzanas Fuji",
                categoria = "Frutas Frescas",
                precio = 1200.0,
                descripcion = "Manzanas Fuji crujientes y dulces, cultivadas en el Valle del Maule.",
                imagenUrl = R.drawable.manzanas
            ),
            Producto(
                id = "VR001",
                nombre = "Zanahorias Orgánicas",
                categoria = "Verduras Orgánicas",
                precio = 900.0,
                descripcion = "Zanahorias crujientes cultivadas sin pesticidas en la Región de O'Higgins.",
                imagenUrl = R.drawable.zanahorias
            ),
            Producto(
                id = "FR002",
                nombre = "Naranjas Valencia",
                categoria = "Frutas Frescas",
                precio = 1000.0,
                descripcion = "Jugosas y ricas en vitamina C, ideales para zumos frescos y refrescantes.",
                imagenUrl = R.drawable.naranjas
            ),
            Producto(
                id = "PO001",
                nombre = "Miel Orgánica",
                categoria = "Productos Orgánicos",
                precio = 5000.0,
                descripcion = "Miel pura y orgánica producida por apicultores locales. Rica en antioxidantes.",
                imagenUrl = R.drawable.miel
            ),
            Producto(
                id = "VR002",
                nombre = "Espinacas Frescas",
                categoria = "Verduras Orgánicas",
                precio = 700.0,
                descripcion = "Espinacas frescas y nutritivas, perfectas para ensaladas y batidos verdes.",
                imagenUrl = R.drawable.espinacas
            ),
            Producto(
                id = "FR003",
                nombre = "Plátanos Cavendish",
                categoria = "Frutas Frescas",
                precio = 800.0,
                descripcion = "Plátanos maduros y dulces, perfectos para el desayuno o como snack energético.",
                imagenUrl = R.drawable.platanos
            )
        )
    }
}