package com.example.app_definida.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.app_definida.model.CartProduct
import com.example.app_definida.navigation.AppRoute
import com.example.app_definida.viewmodel.CartViewModel
import com.example.app_definida.viewmodel.MainViewModel

@Composable
fun CartScreen(
    cartViewModel: CartViewModel,
    mainViewModel: MainViewModel
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
                    CartItemRow(cartProduct = cartItem, viewModel = cartViewModel)
                    Divider()
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            ResumenCompra(
                subtotal = cartState.subtotal,
                envio = cartState.costoEnvio,
                total = cartState.total
            )

            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    // ¡SOLUCIÓN! Usar la ruta correcta que tú definiste.
                    mainViewModel.navigateTo(AppRoute.Payment)
                },
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
fun ResumenCompra(subtotal: Double, envio: Double, total: Double) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("Subtotal:")
            Text("$${String.format("%,.0f", subtotal)} CLP")
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("Envío:")
            Text("$${String.format("%,.0f", envio)} CLP")
        }
        Divider(modifier = Modifier.padding(vertical = 8.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("Total:", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
            Text("$${String.format("%,.0f", total)} CLP", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium, color = Color(0xFF2E8B57))
        }
    }
}
