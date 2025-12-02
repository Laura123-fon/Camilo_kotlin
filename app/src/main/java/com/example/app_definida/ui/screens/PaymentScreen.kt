package com.example.app_definida.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.app_definida.ui.theme.GrisMedio
import com.example.app_definida.viewmodel.CartViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun PaymentScreen(
    cartViewModel: CartViewModel,
    onBackClick: () -> Unit
) {
    val cartState by cartViewModel.uiState.collectAsState()
    
    // Guardamos una copia de los items para mostrar en el ticket incluso después de vaciar el carrito
    val ticketItems = remember { mutableStateOf(cartState.items) }
    val ticketSubtotal = remember { mutableStateOf(cartState.subtotal) }
    val ticketTotal = remember { mutableStateOf(cartState.total) }

    // Fecha actual
    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
    val currentDate = dateFormat.format(Date())
    val currentTime = timeFormat.format(Date())

    DisposableEffect(Unit) {
        onDispose {
            // Se ejecuta cuando la pantalla deja de ser visible
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
        // Botón de volver
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
            tint = Color(0xFF2E8B57), // VerdeEsmeralda
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
            color = GrisMedio
        )
        Spacer(modifier = Modifier.height(24.dp))

        // Boleta estilo Ticket
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFFAFAFA)), // Color papel claro
            shape = MaterialTheme.shapes.small
        ) {
            Column(
                modifier = Modifier.padding(vertical = 20.dp, horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Encabezado Ticket
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
                
                // Datos Boleta
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                     Text("Fecha: $currentDate", style = MaterialTheme.typography.bodySmall, fontFamily = FontFamily.Monospace)
                     Text("Hora: $currentTime", style = MaterialTheme.typography.bodySmall, fontFamily = FontFamily.Monospace)
                }
                
                 Spacer(modifier = Modifier.height(8.dp))
                 HorizontalDivider(thickness = 1.dp, color = Color.Black)
                 Spacer(modifier = Modifier.height(8.dp))
                 
                // Items - Usamos ticketItems para mostrar lo que se compró aunque el carrito se vacíe
                ticketItems.value.forEach { cartItem ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 2.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        // Nombre producto + cantidad
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "000${cartItem.id} ${cartItem.nombre.uppercase()}",
                                style = MaterialTheme.typography.bodyMedium,
                                fontFamily = FontFamily.Monospace
                            )
                            Text(
                                text = "${cartItem.cantidad} UN x ${"%,.0f".format(cartItem.precio)}",
                                style = MaterialTheme.typography.bodySmall,
                                fontFamily = FontFamily.Monospace
                            )
                        }
                        
                        // Total Item
                        Text(
                            text = "$ ${"%,.0f".format(cartItem.precio * cartItem.cantidad)}",
                            style = MaterialTheme.typography.bodyMedium,
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                // Totales estilo ticket
                
                Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.End) {
                    TicketTotalRow("SUBTOTAL", ticketSubtotal.value)

                    val neto = ticketTotal.value / 1.19
                    val iva = ticketTotal.value - neto
                    
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
                            "%,.0f".format(ticketTotal.value), 
                            style = MaterialTheme.typography.titleMedium, 
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Pie de Ticket
                Text(
                    text = "VUELTO $ 0", 
                    style = MaterialTheme.typography.bodyMedium,
                    fontFamily = FontFamily.Monospace,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Start
                )
                 Text(
                    text = "NUMERO DE ARTIC VEND = ${ticketItems.value.sumOf { it.cantidad }}", 
                    style = MaterialTheme.typography.bodyMedium,
                    fontFamily = FontFamily.Monospace,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Start
                )
                
                 Spacer(modifier = Modifier.height(24.dp))
                 
                 // Código de barras simulado (visual)
                 Box(modifier = Modifier
                     .height(40.dp)
                     .fillMaxWidth()
                     .background(Color.Black)) // Solo una barra negra simulando código denso
                 
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
        horizontalArrangement = Arrangement.End // Alinear a la derecha como el ticket
    ) {
        Text(
            text = titulo.padEnd(15), // Intentar alinear texto
            style = MaterialTheme.typography.bodyMedium,
            fontFamily = FontFamily.Monospace,
            textAlign = TextAlign.End
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = "%,.0f".format(valor),
            style = MaterialTheme.typography.bodyMedium,
            fontFamily = FontFamily.Monospace,
            textAlign = TextAlign.End
        )
    }
}
