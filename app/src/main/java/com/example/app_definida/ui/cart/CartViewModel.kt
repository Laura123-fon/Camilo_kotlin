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
                val cartItems = cartProducts.map { cp ->
                    CartItem(
                        producto = Product(
                            id = cp.id,
                            nombre = cp.nombre,
                            descripcion = cp.descripcion,
                            precio = cp.precio,
                            stock = cp.stock,
                            imagenUrl = cp.imagenUrl,
                            origen = cp.origen,
                            sostenibilidad = cp.sostenibilidad,
                            categoria = cp.categoria,
                            receta = cp.receta,
                            calificacion = cp.calificacion,
                            descuento = cp.descuento
                        ),
                        cantidad = cp.cantidad
                    )
                }

                val subtotal = cartItems.sumOf { it.producto.precio * it.cantidad }
                val totalDescuento = cartItems.sumOf { 
                    (it.producto.precio * (it.producto.descuento / 100.0)) * it.cantidad 
                }
                val costoEnvio = if (subtotal > 0) 3000.0 else 0.0
                val totalFinal = (subtotal - totalDescuento) + costoEnvio
                
                _uiState.update {
                    it.copy(
                        items = cartItems,
                        subtotal = subtotal,
                        descuentoTotal = totalDescuento,
                        costoEnvio = costoEnvio,
                        total = totalFinal
                    )
                }
            }
        }
    }

    fun agregarProducto(producto: Product) {
        viewModelScope.launch {
            val itemExistente = dao.getById(producto.id)
            if (itemExistente != null) {
                dao.update(itemExistente.copy(cantidad = itemExistente.cantidad + 1))
            } else {
                dao.insert(
                    CartProduct(
                        id = producto.id,
                        nombre = producto.nombre,
                        precio = producto.precio,
                        imagenUrl = producto.imagenUrl,
                        cantidad = 1,
                        descuento = producto.descuento,
                        descripcion = producto.descripcion,
                        origen = producto.origen,
                        sostenibilidad = producto.sostenibilidad,
                        categoria = producto.categoria,
                        receta = producto.receta,
                        calificacion = producto.calificacion,
                        stock = producto.stock
                    )
                )
            }
        }
    }
    
    fun eliminarProducto(cartItem: CartItem) {
        viewModelScope.launch {
            val cp = dao.getById(cartItem.producto.id)
            if (cp != null) {
                if (cp.cantidad > 1) {
                    dao.update(cp.copy(cantidad = cp.cantidad - 1))
                } else {
                    dao.delete(cp)
                }
            }
        }
    }

    fun vaciarCarrito() {
        viewModelScope.launch {
            dao.deleteAll()
        }
    }
}
