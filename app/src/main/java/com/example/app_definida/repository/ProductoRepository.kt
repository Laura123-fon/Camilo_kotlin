package com.example.app_definida.repository

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
                imagenUrl = "https://images.unsplash.com/photo-1567306226416-28f0efdc88ce?w=500&q=80"
            ),
            Producto(
                id = "VR001",
                nombre = "Zanahorias Orgánicas",
                categoria = "Verduras Orgánicas",
                precio = 900.0,
                descripcion = "Zanahorias crujientes cultivadas sin pesticidas en la Región de O'Higgins.",
                imagenUrl = "https://images.unsplash.com/photo-1590868309235-ea34bed7bd7f?w=500&q=80"
            ),
            Producto(
                id = "FR002",
                nombre = "Naranjas Valencia",
                categoria = "Frutas Frescas",
                precio = 1000.0,
                descripcion = "Jugosas y ricas en vitamina C, ideales para zumos frescos y refrescantes.",
                imagenUrl = "https://images.unsplash.com/photo-1547514701-42782101795e?w=500&q=80"
            ),
            Producto(
                id = "PO001",
                nombre = "Miel Orgánica",
                categoria = "Productos Orgánicos",
                precio = 5000.0,
                descripcion = "Miel pura y orgánica producida por apicultores locales. Rica en antioxidantes.",
                imagenUrl = "https://images.unsplash.com/photo-1558642452-9d2a7deb7f62?w=500&q=80"
            ),
            Producto(
                id = "VR002",
                nombre = "Espinacas Frescas",
                categoria = "Verduras Orgánicas",
                precio = 700.0,
                descripcion = "Espinacas frescas y nutritivas, perfectas para ensaladas y batidos verdes.",
                imagenUrl = "https://images.unsplash.com/photo-1576045057995-568f588f21fb?w=500&q=80"
            ),
            Producto(
                id = "FR003",
                nombre = "Plátanos Cavendish",
                categoria = "Frutas Frescas",
                precio = 800.0,
                descripcion = "Plátanos maduros y dulces, perfectos para el desayuno o como snack energético.",
                imagenUrl = "https://images.unsplash.com/photo-1528825871115-3581a5387919?w=500&q=80"
            )
        )
    }
}