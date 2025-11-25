package com.example.app_definida.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.app_definida.ui.theme.GrisMedio
import com.example.app_definida.viewmodel.CartViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun PaymentScreen() {
    val cartViewModel: CartViewModel = viewModel()
    val cartState by cartViewModel.uiState.collectAsState()

    // Fecha actual
    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
    val currentDate = dateFormat.format(Date())
    val currentTime = timeFormat.format(Date())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
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
                    text = "SUPERMERCADO LIDER", // Ejemplo genérico, puedes poner tu nombre de app
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
                 
                // Items
                cartState.items.forEach { cartItem ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 2.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        // Nombre producto + cantidad
                        Column(modifier = Modifier.weight(1f)) {
                             // Código simulado
                            Text(
                                text = "000${cartItem.producto.id} ${cartItem.producto.nombre.uppercase()}",
                                style = MaterialTheme.typography.bodyMedium,
                                fontFamily = FontFamily.Monospace
                            )
                            Text(
                                text = "${cartItem.cantidad} UN x ${"%,.0f".format(cartItem.producto.precio)}",
                                style = MaterialTheme.typography.bodySmall,
                                fontFamily = FontFamily.Monospace
                            )
                        }
                        
                        // Total Item
                        Text(
                            text = "$ ${"%,.0f".format(cartItem.producto.precio * cartItem.cantidad)}",
                            style = MaterialTheme.typography.bodyMedium,
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                // Totales estilo ticket
                
                Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.End) {
                    TicketTotalRow("SUBTOTAL", cartState.subtotal)
                    // IVA 19% (Asumido incluido o desglose según se quiera, aquí simulando la imagen)
                    // La imagen muestra TOTAL AFECTO, EXENTO, IVA. Simplificaremos a lo visual.
                    // Calculando valores simulados de IVA sobre el total para mostrar parecido
                    val neto = cartState.total / 1.19
                    val iva = cartState.total - neto
                    
                    TicketTotalRow("TOTAL AFECTO $", neto)
                    TicketTotalRow("TOTAL EXENTO $", 0.0) // Si hubiera productos exentos
                    TicketTotalRow("TOTAL IVA(19.0%)$", iva)
                    
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
                            "%,.0f".format(cartState.total), 
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
                    text = "NUMERO DE ARTIC VEND = ${cartState.items.sumOf { it.cantidad }}", 
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
