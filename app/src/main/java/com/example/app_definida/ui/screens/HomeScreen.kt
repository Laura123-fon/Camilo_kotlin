package com.example.app_definida.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.app_definida.model.Producto
import com.example.app_definida.viewmodel.CartViewModel
import com.example.app_definida.viewmodel.MainViewModel
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(cartViewModel: CartViewModel) {
    val mainViewModel: MainViewModel = viewModel()
    val productos by mainViewModel.productos.collectAsState()

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9)),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Nuestra Misión",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF2E8B57)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Proporcionar productos frescos y de calidad directamente desde el campo hasta tu puerta, fomentando una conexión más cercana con los agricultores locales y una alimentación saludable.",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Nuestra Visión",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF2E8B57)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Ser la tienda online líder en Chile, reconocida por nuestra calidad excepcional, servicio al cliente y compromiso con la sostenibilidad.",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }

        items(
            items = productos,
            key = { it.id }
        ) { producto ->

            AnimatedVisibility(
                visible = true,
                enter = fadeIn(animationSpec = tween(durationMillis = 500)),
                exit = fadeOut(animationSpec = tween(durationMillis = 500))
            ) {
                ProductoCard(producto = producto, cartViewModel = cartViewModel)
            }
        }
    }
}
@Composable
fun ProductoCard(producto: Producto, cartViewModel: CartViewModel) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = producto.imagenUrl,
                contentDescription = "Imagen de ${producto.nombre}",
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 16.dp)
            ) {
                Text(
                    text = producto.nombre,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF333333)
                )
                Text(
                    text = producto.categoria,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
                Text(
                    text = "$ ${"%,.0f".format(producto.precio).replace(",",".")} CLP",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF2E8B57),
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
            IconButton(onClick = {
                cartViewModel.agregarProducto(producto)
            }) {
                Icon(
                    imageVector = Icons.Default.ShoppingCart,
                    contentDescription = "Añadir al carrito",
                    tint = Color(0xFF2E8B57)
                )
            }
        }
    }
}
