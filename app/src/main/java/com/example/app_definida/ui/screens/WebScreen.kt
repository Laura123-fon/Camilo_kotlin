package com.example.app_definida.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp // Comentado si no tienes la imagen
import coil.compose.AsyncImage


@Composable
fun WebScreen(
    onContinuar: (() -> Unit)? = null // <-- parámetro para navegar al registro
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF6F9F6))
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Imagen principal tipo banner
        AsyncImage(
            model = "https://images.unsplash.com/photo-1501004318641-b39e6451bec6?auto=format&fit=crop&w=1200&q=60",
            contentDescription = "Huerto Hogar Banner",
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(bottom = 16.dp),
            contentScale = ContentScale.Crop
        )

        Text(
            text = "Huerto-Hogar 🍎🥦",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF2E8B57),
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Cultiva bienestar en tu hogar con productos frescos, naturales y locales.",
            fontSize = 18.sp,
            color = Color(0xFF4B4B4B),
            lineHeight = 26.sp,
            modifier = Modifier.padding(horizontal = 12.dp),
        )

        Spacer(modifier = Modifier.height(24.dp))

        AsyncImage(
            model = "https://images.unsplash.com/photo-1556912167-f556f1f39dfb?auto=format&fit=crop&w=1200&q=60",
            contentDescription = "Huerto Orgánico",
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .clip(MaterialTheme.shapes.medium),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = { onContinuar?.invoke() },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E8B57)),
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(50.dp)
        ) {
            Text("Explorar HuertoHogar", fontSize = 18.sp, color = Color.White)
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "🌿 Productos locales, orgánicos y sostenibles",
            fontSize = 14.sp,
            color = Color.Gray
        )
    }
}