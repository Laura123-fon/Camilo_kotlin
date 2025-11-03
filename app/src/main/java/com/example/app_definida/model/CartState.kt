package com.example.app_definida.model

data class CartItem(
    val producto: Producto,
    val cantidad: Int = 1
)

// Representa el estado completo del carrito en un momento dado
data class CartUiState(
    val items: List<CartItem> = emptyList(),
    val subtotal: Double = 0.0,
    val costoEnvio: Double = 3000.0, // Costo de envío fijo
    val total: Double = 0.0
)