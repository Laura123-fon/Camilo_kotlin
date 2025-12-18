package com.example.app_definida.ui.checkout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.app_definida.ui.cart.CartViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun PaymentScreen(
    cartViewModel: CartViewModel,
    onBackClick: () -> Unit
) {
    val cartState by cartViewModel.uiState.collectAsState()

    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
    val currentDate = dateFormat.format(Date())
    val currentTime = timeFormat.format(Date())

    DisposableEffect(Unit) {
        onDispose {
            cartViewModel.vaciarCarrito()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            IconButton(onClick = onBackClick) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, "Volver", tint = Color.Black)
            }
        }

        Icon(
            Icons.Default.CheckCircle,
            "Éxito",
            tint = Color(0xFF2E8B57),
            modifier = Modifier.size(64.dp)
        )

        Text(
            text = "¡Pago Confirmado!",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF2E8B57)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // TICKET DE COMPRA
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(4.dp)
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "HUERTO HOGAR S.A.",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Monospace
                )
                Text("GIRO: PRODUCTOS ORGANICOS", style = MaterialTheme.typography.labelSmall, fontFamily = FontFamily.Monospace)
                Text("CASA MATRIZ: AV. AGRICULTURA 123", style = MaterialTheme.typography.labelSmall, fontFamily = FontFamily.Monospace)

                Spacer(modifier = Modifier.height(16.dp))
                HorizontalDivider(thickness = 1.dp, color = Color.LightGray)

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("FECHA: $currentDate", style = MaterialTheme.typography.labelSmall, fontFamily = FontFamily.Monospace)
                    Text("HORA: $currentTime", style = MaterialTheme.typography.labelSmall, fontFamily = FontFamily.Monospace)
                }

                Spacer(modifier = Modifier.height(16.dp))

                // LISTA DE PRODUCTOS
                cartState.items.forEach { item ->
                    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                        Text(
                            text = item.producto.nombre.uppercase(),
                            style = MaterialTheme.typography.bodyMedium,
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.Bold
                        )
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            // CORRECCIÓN AQUÍ: Se eliminó \"
                            Text(
                                text = "${item.cantidad} x $${String.format(Locale.getDefault(), "%,.0f", item.producto.precio)}",
                                style = MaterialTheme.typography.bodySmall,
                                fontFamily = FontFamily.Monospace
                            )
                            // CORRECCIÓN AQUÍ: Se eliminó \"
                            Text(
                                text = "$${String.format(Locale.getDefault(), "%,.0f", item.producto.precio * item.cantidad)}",
                                style = MaterialTheme.typography.bodySmall,
                                fontFamily = FontFamily.Monospace
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                HorizontalDivider(thickness = 1.dp, color = Color.Black)
                Spacer(modifier = Modifier.height(12.dp))

                // TOTALES
                TicketRow("SUBTOTAL", cartState.subtotal)

                if (cartState.descuentoTotal > 0) {
                    TicketRow("AHORRO TOTAL", -cartState.descuentoTotal, Color(0xFFE53935))
                }

                TicketRow("COSTO ENVIO", cartState.costoEnvio)

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        "TOTAL A PAGAR",
                        style = MaterialTheme.typography.titleLarge,
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.ExtraBold
                    )
                    // CORRECCIÓN AQUÍ: Se eliminó \"
                    Text(
                        "$${String.format(Locale.getDefault(), "%,.0f", cartState.total)}",
                        style = MaterialTheme.typography.titleLarge,
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.ExtraBold
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    "GRACIAS POR PREFERIR LO NATURAL",
                    style = MaterialTheme.typography.labelMedium,
                    fontFamily = FontFamily.Monospace,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp)
                        .background(Color.Black.copy(alpha = 0.05f))
                ) {
                    Text(
                        "|| ||| || |||| || ||| ||| ||",
                        modifier = Modifier.align(Alignment.Center),
                        fontFamily = FontFamily.Monospace,
                        fontSize = 20.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = onBackClick,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E8B57))
        ) {
            Text("Volver al Inicio", color = Color.White)
        }
    }
}

@Composable
fun TicketRow(label: String, amount: Double, color: Color = Color.Unspecified) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 2.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, style = MaterialTheme.typography.bodyMedium, fontFamily = FontFamily.Monospace)
        Text(
            text = "$${String.format(Locale.getDefault(), "%,.0f", amount)}",
            style = MaterialTheme.typography.bodyMedium,
            fontFamily = FontFamily.Monospace,
            color = color,
            fontWeight = if (color != Color.Unspecified) FontWeight.Bold else FontWeight.Normal
        )
    }
}
