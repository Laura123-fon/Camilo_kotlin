package com.example.app_definida.ui.components

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun ImagenInteligente(
    modifier: Modifier = Modifier,
    uri: Uri?
) {
    if (uri != null) {
        AsyncImage(
            model = uri,
            contentDescription = "Imagen de perfil",
            modifier = modifier
                .size(128.dp)
                .clip(CircleShape)
                .border(2.dp, Color.Gray, CircleShape),
            contentScale = ContentScale.Crop
        )
    } else {
        Image(
            imageVector = Icons.Default.AccountCircle,
            contentDescription = "Ícono de perfil por defecto",
            modifier = modifier.size(128.dp),
            colorFilter = ColorFilter.tint(Color.Gray)
        )
    }
}