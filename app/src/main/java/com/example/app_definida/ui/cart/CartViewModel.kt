package com.example.app_definida.ui.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_definida.data.local.CartProductDao
import com.example.app_definida.data.model.CartProduct
import com.example.app_definida.data.model.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CartViewModel(private val dao: CartProductDao) : ViewModel() {

    private val _uiState = MutableStateFlow(CartUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            dao.getAll().collect { cartProducts ->
                val cartItems = cartProducts.map { cartProduct ->
                    CartItem(
                        producto = Product(
                            id = cartProduct.id,
                            nombre = cartProduct.nombre,
                            descripcion = "",
                            precio = cartProduct.precio,
                            categoria = "",
                            imagenUrl = cartProduct.imagenUrl,
                            descuento = cartProduct.descuento
                        ),
                        cantidad = cartProduct.cantidad
                    )
                }

                val subtotal = cartItems.sumOf { it.producto.precio * it.cantidad }
                val costoEnvio = if (subtotal > 0) 3000.0 else 0.0
                
                _uiState.update {
                    it.copy(
                        items = cartItems,
                        subtotal = subtotal,
                        costoEnvio = costoEnvio,
                        total = subtotal + costoEnvio
                    )
                }
            }
        }
    }

    fun agregarProducto(producto: Product) {
        viewModelScope.launch {
            val itemExistente = dao.getById(producto.id)
            if (itemExistente != null) {
                val updatedItem = itemExistente.copy(cantidad = itemExistente.cantidad + 1)
                dao.update(updatedItem)
            } else {
                val newItem = CartProduct(
                    id = producto.id,
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
    
    fun eliminarProducto(cartItem: CartItem) {
        viewModelScope.launch {
            val cartProductToDelete = CartProduct(
                id = cartItem.producto.id,
                nombre = cartItem.producto.nombre,
                precio = cartItem.producto.precio,
                imagenUrl = cartItem.producto.imagenUrl,
                cantidad = cartItem.cantidad,
                descuento = cartItem.producto.descuento
            )
            dao.delete(cartProductToDelete)
        }
    }

    fun vaciarCarrito() {
        viewModelScope.launch {
            dao.deleteAll()
        }
    }
}
