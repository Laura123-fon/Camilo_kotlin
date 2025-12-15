package com.example.app_definida.ui.checkout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Volver",
                    tint = Color.Black
                )
            }
        }

        Icon(
            imageVector = Icons.Filled.CheckCircle,
            contentDescription = "Pago Exitoso",
            tint = Color(0xFF2E8B57),
            modifier = Modifier.size(60.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "¡Gracias por tu compra!",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Tu pedido ha sido procesado con éxito.",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(24.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFFAFAFA)),
            shape = MaterialTheme.shapes.small
        ) {
            Column(
                modifier = Modifier.padding(vertical = 20.dp, horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "HuertoHogar",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Monospace
                )
                Text(
                    text = "R.U.T.: 76.123.456-7",
                    style = MaterialTheme.typography.bodySmall,
                    fontFamily = FontFamily.Monospace
                )
                Text(
                    text = "BOLETA ELECTRONICA",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Monospace,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                     Text("Fecha: $currentDate", style = MaterialTheme.typography.bodySmall, fontFamily = FontFamily.Monospace)
                     Text("Hora: $currentTime", style = MaterialTheme.typography.bodySmall, fontFamily = FontFamily.Monospace)
                }
                
                 Spacer(modifier = Modifier.height(8.dp))
                 HorizontalDivider(thickness = 1.dp, color = Color.Black)
                 Spacer(modifier = Modifier.height(8.dp))
                 
                cartState.items.forEach { cartItem ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 2.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "000${cartItem.producto.id} ${cartItem.producto.nombre.uppercase()}",
                                style = MaterialTheme.typography.bodyMedium,
                                fontFamily = FontFamily.Monospace
                            )
                            Text(
                                text = "${cartItem.cantidad} UN x $${String.format(Locale.getDefault(), "%,.0f", cartItem.producto.precio)}",
                                style = MaterialTheme.typography.bodySmall,
                                fontFamily = FontFamily.Monospace
                            )
                        }
                        
                        Text(
                            text = "$ ${String.format(Locale.getDefault(), "%,.0f", cartItem.producto.precio * cartItem.cantidad)}",
                            style = MaterialTheme.typography.bodyMedium,
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                
                Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.End) {
                    TicketTotalRow("SUBTOTAL", cartState.subtotal)

                    val neto = cartState.total / 1.19
                    val iva = cartState.total - neto
                    
                    TicketTotalRow("TOTAL AFECTO $", neto)
                    TicketTotalRow("TOTAL EXENTO $", 0.0)
                    TicketTotalRow("TOTAL IVA(19.0%)", iva)
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            "TOTAL $", 
                            style = MaterialTheme.typography.titleMedium, 
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            String.format(Locale.getDefault(), "%,.0f", cartState.total), 
                            style = MaterialTheme.typography.titleMedium, 
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Text(
                    text = "VUELTO $ 0", 
                    style = MaterialTheme.typography.bodyMedium,
                    fontFamily = FontFamily.Monospace,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Start
                )
                 Text(
                    text = "NUMERO DE ARTIC VEND = ${cartState.items.sumOf { it.cantidad }}", 
                    style = MaterialTheme.typography.bodyMedium,
                    fontFamily = FontFamily.Monospace,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Start
                )
                
                 Spacer(modifier = Modifier.height(24.dp))
                 
                 Box(modifier = Modifier
                     .height(40.dp)
                     .fillMaxWidth()
                     .background(Color.Black))
                 
                 Spacer(modifier = Modifier.height(4.dp))
                 Text(
                    text = "TIMBRE ELECTRONICO SII",
                    style = MaterialTheme.typography.labelSmall,
                     fontFamily = FontFamily.Monospace
                 )
                 Text(
                    text = "Res. 80 de 2014 Verifique documento: www.sii.cl",
                    style = MaterialTheme.typography.labelSmall,
                    fontFamily = FontFamily.Monospace,
                    textAlign = TextAlign.Center,
                    fontSize = 10.sp
                 )

            }
        }
    }
}

@Composable
fun TicketTotalRow(titulo: String, valor: Double) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End
    ) {
        Text(
            text = titulo.padEnd(15),
            style = MaterialTheme.typography.bodyMedium,
            fontFamily = FontFamily.Monospace,
            textAlign = TextAlign.End
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = String.format(Locale.getDefault(), "%,.0f", valor),
            style = MaterialTheme.typography.bodyMedium,
            fontFamily = FontFamily.Monospace,
            textAlign = TextAlign.End
        )
    }
}
