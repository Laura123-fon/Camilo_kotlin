package com.example.app_definida.ui.state

import com.example.app_definida.model.CartItem // -> Importa desde 'model'

/**
 * Representa el estado completo de la pantalla del carrito.
 */
data class CartUiState(
    val items: List<CartItem> = emptyList(),
    val subtotal: Double = 0.0,
    val costoEnvio: Double = 3000.0, // Costo de envío fijo
    val total: Double = 0.0
)
