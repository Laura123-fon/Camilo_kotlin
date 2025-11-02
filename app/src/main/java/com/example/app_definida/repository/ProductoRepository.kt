package com.example.app_definida.repository

import com.example.app_definida.model.Producto

class ProductoRepository {
    fun getProductos(): List<Producto> {
        return listOf(
            // --- FRUTAS FRESCAS ---
            Producto(
                id = "FR001",
                nombre = "Manzanas Fuji",
                descripcion = "Frescas y crujientes, directo desde Los Andes.",
                precio = 2500.0,
                categoria = "Frutas Frescas",
                origen = "Los Andes"
            ),
            Producto(
                id = "FR002",
                nombre = "Naranjas Valencia",
                descripcion = "Jugosas y dulces, ideales para jugos naturales.",
                precio = 2300.0,
                categoria = "Frutas Frescas",
                origen = "Valparaíso"
            ),
            Producto(
                id = "FR003",
                nombre = "Plátanos Cavendish",
                descripcion = "Ricos en potasio, perfectos para un snack saludable.",
                precio = 2100.0,
                categoria = "Frutas Frescas",
                origen = "Concepción"
            ),

            // --- VERDURAS ORGÁNICAS ---
            Producto(
                id = "VR001",
                nombre = "Zanahorias Orgánicas",
                descripcion = "Cultivadas sin pesticidas, llenas de vitaminas.",
                precio = 1800.0,
                categoria = "Verduras Orgánicas",
                origen = "Nacimiento"
            ),
            Producto(
                id = "VR002",
                nombre = "Espinacas Frescas",
                descripcion = "Tiernas y verdes, ideales para ensaladas o salteados.",
                precio = 1600.0,
                categoria = "Verduras Orgánicas",
                origen = "Viña del Mar"
            ),
            Producto(
                id = "VR003",
                nombre = "Pimientos Tricolores",
                descripcion = "Rojos, verdes y amarillos — una explosión de color y sabor.",
                precio = 2700.0,
                categoria = "Verduras Orgánicas",
                origen = "Santiago"
            ),

            // --- PRODUCTOS ORGÁNICOS ---
            Producto(
                id = "PO001",
                nombre = "Miel Orgánica",
                descripcion = "Endulzante natural producido por apicultores locales.",
                precio = 4500.0,
                categoria = "Productos Orgánicos",
                origen = "Valdivia"
            ),
            Producto(
                id = "PO002",
                nombre = "Quinua Orgánica",
                descripcion = "Grano andino rico en proteínas y fibra, perfecto para comidas nutritivas.",
                precio = 3800.0,
                categoria = "Productos Orgánicos",
                origen = "Villarica"
            ),

            // --- PRODUCTOS LÁCTEOS ---
            Producto(
                id = "PL001",
                nombre = "Leche Entera",
                descripcion = "Producida localmente, ideal para toda la familia.",
                precio = 2200.0,
                categoria = "Productos Lácteos",
                origen = "Puerto Montt"
            )

        )
    }
}