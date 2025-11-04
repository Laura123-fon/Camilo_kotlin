package com.example.app_definida.ui.state

import com.example.app_definida.model.Producto
data class CartItem(
    val producto: Producto,
    val cantidad: Int = 1
)
data class CartUiState(
    val items: List<CartItem> = emptyList(),
    val subtotal: Double = 0.0,
    val costoEnvio: Double = 3000.0,
    val total: Double = 0.0
)
