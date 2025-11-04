package com.example.app_definida.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.app_definida.ui.theme.GrisMedio
import com.example.app_definida.viewmodel.CartViewModel

@Composable
fun PaymentScreen() {
    val cartViewModel: CartViewModel = viewModel()
    val cartState by cartViewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Filled.CheckCircle,
            contentDescription = "Pago Exitoso",
            tint = Color(0xFF2E8B57), // VerdeEsmeralda
            modifier = Modifier.size(80.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "¡Gracias por tu compra!",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Tu pedido ha sido procesado con éxito.",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyLarge,
            color = GrisMedio
        )
        Spacer(modifier = Modifier.height(24.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Resumen del Pedido:", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                Divider()
                cartState.items.forEach { cartItem ->
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("${cartItem.cantidad}x ${cartItem.producto.nombre}")
                        Text("$${"%,.0f".format(cartItem.producto.precio * cartItem.cantidad)} CLP")
                    }
                }
                Divider(modifier = Modifier.padding(vertical = 8.dp))
                // Totales
                ResumenCompra(subtotal = cartState.subtotal, envio = cartState.costoEnvio, total = cartState.total)
            }
        }
    }
}
