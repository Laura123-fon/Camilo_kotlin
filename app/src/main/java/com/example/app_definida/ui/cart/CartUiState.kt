package com.example.app_definida.ui.cart

import com.example.app_definida.data.model.Product

data class CartItem(
    val producto: Product,
    val cantidad: Int = 1
)

data class CartUiState(
    val items: List<CartItem> = emptyList(),
    val subtotal: Double = 0.0,
    val descuentoTotal: Double = 0.0,
    val costoEnvio: Double = 0.0,
    val total: Double = 0.0
)
