package com.example.app_definida.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.app_definida.data.model.Product
import java.util.Locale

@Composable
fun ProductoCard(product: Product, onAddToCart: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column {
            Box {
                AsyncImage(
                    model = product.imagenUrl,
                    contentDescription = "Imagen de ${product.nombre}",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
                    contentScale = ContentScale.Crop
                )
                
                if (product.descuento > 0) {
                    Surface(
                        color = Color(0xFFE53935),
                        shape = RoundedCornerShape(bottomEnd = 8.dp),
                        modifier = Modifier.align(Alignment.TopStart)
                    ) {
                        Text(
                            text = "¡Descuento del ${product.descuento.toInt()}%!",
                            color = Color.White,
                            style = MaterialTheme.typography.labelMedium,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Surface(
                    color = Color.Black.copy(alpha = 0.6f),
                    shape = RoundedCornerShape(topStart = 12.dp),
                    modifier = Modifier.align(Alignment.BottomEnd)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.Star, "Calificación", tint = Color(0xFFFFD700), modifier = Modifier.size(14.dp))
                        Spacer(Modifier.width(4.dp))
                        Text(text = product.calificacion.toString(), color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }

            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = product.nombre,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF2E3D49)
                        )
                        Text(
                            text = "Origen: ${product.origen}",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Gray
                        )
                    }
                    
                    IconButton(
                        onClick = onAddToCart,
                        modifier = Modifier.background(Color(0xFF2E8B57).copy(alpha = 0.1f), CircleShape)
                    ) {
                        Icon(Icons.Default.AddShoppingCart, "Add", tint = Color(0xFF2E8B57))
                    }
                }

                Spacer(Modifier.height(8.dp))
                
                Text(
                    text = product.descripcion,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.DarkGray,
                    maxLines = 2
                )

                Spacer(Modifier.height(12.dp))

                Row(verticalAlignment = Alignment.Bottom) {
                    if (product.descuento > 0) {
                        val ahorro = product.precio * (product.descuento / 100.0)
                        val precioFinal = product.precio - ahorro
                        
                        Text(
                            text = "$${String.format(Locale.getDefault(), "%,.0f", product.precio)}",
                            style = MaterialTheme.typography.bodyMedium.copy(textDecoration = TextDecoration.LineThrough),
                            color = Color.Gray
                        )
                        Spacer(Modifier.width(8.dp))
                        Text(
                            text = "$${String.format(Locale.getDefault(), "%,.0f", precioFinal)}",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color(0xFFE53935)
                        )
                    } else {
                        Text(
                            text = "$${String.format(Locale.getDefault(), "%,.0f", product.precio)}",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color(0xFF2E8B57)
                        )
                    }
                }
                
                if (product.receta.isNotEmpty()) {
                    Text(
                        text = "💡 Sugerencia: ${product.receta}",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color(0xFF2E8B57),
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }
        }
    }
}
