package com.example.app_definida.viewmodel

import androidx.lifecycle.ViewModel

import com.example.app_definida.model.Producto
import com.example.app_definida.ui.state.CartItem
import com.example.app_definida.ui.state.CartUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CartViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(CartUiState())

    val uiState = _uiState.asStateFlow()

    fun agregarProducto(producto: Producto) {
        _uiState.update { currentState ->
            val itemsActuales = currentState.items.toMutableList()
            val itemExistente = itemsActuales.find { it.producto.id == producto.id }

            if (itemExistente != null) {
                val itemIndex = itemsActuales.indexOf(itemExistente)
                itemsActuales[itemIndex] = itemExistente.copy(cantidad = itemExistente.cantidad + 1)
            } else {
                itemsActuales.add(CartItem(producto = producto))
            }

            currentState.copy(items = itemsActuales)
        }
        actualizarTotales()
    }

    fun eliminarProducto(cartItem: CartItem) {
        _uiState.update { currentState ->
            val itemsActuales = currentState.items.toMutableList()
            itemsActuales.remove(cartItem)
            currentState.copy(items = itemsActuales)
        }
        actualizarTotales()
    }
    
    fun vaciarCarrito() {
        _uiState.update { currentState ->
             currentState.copy(items = emptyList(), subtotal = 0.0, total = 0.0)
        }
    }


    private fun actualizarTotales() {
        _uiState.update { currentState ->
            val subtotal = currentState.items.sumOf { it.producto.precio * it.cantidad }
            val total = subtotal + currentState.costoEnvio
            currentState.copy(subtotal = subtotal, total = total)
        }
    }
}
