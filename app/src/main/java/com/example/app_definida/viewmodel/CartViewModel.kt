package com.example.app_definida.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_definida.data.CartProductDao
import com.example.app_definida.model.CartProduct
import com.example.app_definida.model.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class CartUiState(
    val items: List<CartProduct> = emptyList(),
    val subtotal: Double = 0.0,
    val descuento: Double = 0.0,
    val costoEnvio: Double = 10.0, // Costo de envío de ejemplo
    val total: Double = 0.0
)

class CartViewModel(private val dao: CartProductDao) : ViewModel() {

    private val _uiState = MutableStateFlow(CartUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            dao.getAll().collect { cartProducts ->
                val subtotal = cartProducts.sumOf { it.precio * it.cantidad }
                val descuentoTotal = cartProducts.sumOf { it.descuento * it.cantidad }
                _uiState.update {
                    it.copy(
                        items = cartProducts,
                        subtotal = subtotal,
                        descuento = descuentoTotal,
                        total = subtotal - descuentoTotal + it.costoEnvio
                    )
                }
            }
        }
    }

    // 2. Función actualizada para aceptar el Product de la API

    fun agregarProducto(producto: Product) {
        viewModelScope.launch {
            // El ID del producto de la API es Long, pero en el carrito es String. Hay que convertirlo.
            val productIdStr = producto.id.toString()

            val itemExistente = dao.getById(productIdStr)
            if (itemExistente != null) {
                val updatedItem = itemExistente.copy(cantidad = itemExistente.cantidad + 1)
                dao.update(updatedItem)
            } else {
                // 3. Crear el CartProduct usando los datos del Product de la API
                val newItem = CartProduct(
                    id = productIdStr, // Usar el ID convertido a String
                    nombre = producto.nombre,
                    precio = producto.precio,
                    imagenUrl = producto.imagenUrl,
                    cantidad = 1,
                    descuento = producto.descuento
                )
                dao.insert(newItem)
            }
        }
    }


    fun agregarDescuento(producto: Product) {
        viewModelScope.launch {
            // El ID del producto de la API es Long, pero en el carrito es String. Hay que convertirlo.
            val productIdStr = producto.id.toString()

            val itemExistente = dao.getById(productIdStr)
            if (itemExistente != null) {
                val updatedItem = itemExistente.copy(cantidad = itemExistente.cantidad + 1)
                dao.update(updatedItem)
            } else {
                // 3. Crear el CartProduct usando los datos del Product de la API
                val newItem = CartProduct(
                    id = productIdStr, // Usar el ID convertido a String
                    nombre = producto.nombre,
                    precio = producto.precio,
                    imagenUrl = producto.imagenUrl,
                    cantidad = 1,
                    descuento = producto.descuento
                )
                dao.insert(newItem)
            }
        }
    }
    
    fun eliminarProducto(cartProduct: CartProduct) {
        viewModelScope.launch {
            dao.delete(cartProduct)
        }
    }

    fun vaciarCarrito() {
        viewModelScope.launch {
            dao.deleteAll()
        }
    }
}
