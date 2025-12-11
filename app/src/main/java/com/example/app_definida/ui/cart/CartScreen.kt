package com.example.app_definida.ui.cart

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.app_definida.data.model.CartProduct


@Composable
fun CartScreen(
    cartViewModel: CartViewModel,
    onGoToCheckout: () -> Unit
) {
    val cartState by cartViewModel.uiState.collectAsState()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        if (cartState.items.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Tu carrito está vacío", style = MaterialTheme.typography.titleLarge)
            }
        } else {
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(cartState.items) { cartItem ->
                    CartItemRow(cartProduct = cartItem.producto, viewModel = cartViewModel)
                    Divider()
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            ResumenCompra(
                subtotal = cartState.subtotal,
                total = cartState.total
            )

            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = onGoToCheckout,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E8B57))
            ) {
                Text("Finalizar Compra", color = Color.White)
            }
        }
    }
}

@Composable
fun CartItemRow(cartProduct: CartProduct, viewModel: CartViewModel) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(cartProduct.nombre, fontWeight = FontWeight.Bold)
            Text("Cantidad: ${cartProduct.cantidad}")
            Text("Precio: $${String.format("%,.0f", cartProduct.precio * cartProduct.cantidad)} CLP", color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
        IconButton(onClick = { viewModel.eliminarProducto(cartProduct) }) {
            Icon(Icons.Default.Delete, contentDescription = "Eliminar productos", tint = MaterialTheme.colorScheme.error)
        }
    }
}

@Composable
fun ResumenCompra(subtotal: Double, total: Double) {
    val costoEnvio = if (subtotal > 0) 3000.0 else 0.0
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("Subtotal:")
            Text("$${String.format("%,.0f", subtotal)} CLP")
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("Envío:")
            Text("$${String.format("%,.0f", costoEnvio)} CLP")
        }
        Divider(modifier = Modifier.padding(vertical = 8.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("Total:", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
            Text("$${String.format("%,.0f", total)} CLP", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium, color = Color(0xFF2E8B57))
        }
    }
}
