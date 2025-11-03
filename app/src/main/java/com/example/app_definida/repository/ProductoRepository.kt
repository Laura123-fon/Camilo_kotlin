package com.example.app_definida.repository

import com.example.app_definida.model.Producto

class ProductoRepository {
    fun getProductos(): List<Producto> {
        return listOf(
            Producto("FR001", "Manzanas Fuji", "Crujientes y dulces, cultivadas en el Valle del Maule.", 1200.0, "Frutas Frescas", "https://picsum.photos/seed/FR001/200"),
            Producto("VR001", "Zanahorias Orgánicas", "Excelente fuente de vitamina A y fibra.", 900.0, "Verduras Orgánicas", "https://picsum.photos/seed/VR001/200"),
            Producto("PO001", "Miel Orgánica", "Pura y orgánica, de apicultores locales.", 5000.0, "Productos Orgánicos", "https://picsum.photos/seed/PO001/200"),
            Producto("FR002", "Naranjas Valencia", "Jugosas y ricas en vitamina C.", 1000.0, "Frutas Frescas", "https://picsum.photos/seed/FR002/200"),
            Producto("VR002", "Espinacas Frescas", "Nutritivas, perfectas para ensaladas.", 700.0, "Verduras Orgánicas", "https://picsum.photos/seed/VR002/200")
        )
    }
}
