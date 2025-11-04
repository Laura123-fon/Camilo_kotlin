package com.example.app_definida.ui.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.app_definida.ui.theme.VerdeEsmeralda
import com.example.app_definida.viewmodel.UsuarioViewModel


@Composable
fun ProfileScreen(usuarioViewModel: UsuarioViewModel) {
    // Observamos el estado completo del ViewModel (nombre, correo, foto, etc.)
    val estadoUsuario by usuarioViewModel.estado.collectAsState()

    // 1. EL "LANZADOR" QUE ABRE LA GALERÍA DEL TELÉFONO
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            // Cuando el usuario elige una imagen, guardamos su Uri en el ViewModel.
            uri?.let {
                usuarioViewModel.onFotoPerfilChange(it)
            }
        }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // 2. ÁREA DE LA FOTO DE PERFIL "CLICKEABLE"
        Box(
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .border(2.dp, VerdeEsmeralda, CircleShape)
                .clickable {
                    // Al hacer clic, lanzamos la galería pidiendo cualquier tipo de imagen.
                    imagePickerLauncher.launch("image/*")
                },
            contentAlignment = Alignment.Center
        ) {
            if (estadoUsuario.fotoPerfilUri == null) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Seleccionar foto de perfil",
                    modifier = Modifier.size(80.dp),
                    tint = VerdeEsmeralda
                )
            } else {
                // Si hay foto, usa AsyncImage (de la librería Coil) para mostrarla.
                AsyncImage(
                    model = estadoUsuario.fotoPerfilUri,
                    contentDescription = "Foto de perfil",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop // Asegura que la imagen llene el círculo
                )
            }
        }

        Spacer(Modifier.height(16.dp))

        // Título de la pantalla
        Text(
            text = "Mi Perfil",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.height(24.dp))

        // 3. SECCIÓN PARA MOSTRAR LOS DATOS DEL REGISTRO
        // Esta es la parte que habías perdido y que ahora restauramos.
        ProfileInfoRow(label = "Nombre:", value = estadoUsuario.nombre)
        ProfileInfoRow(label = "Correo:", value = estadoUsuario.correo)
        ProfileInfoRow(label = "Dirección:", value = estadoUsuario.direccion)
    }
}

/**
 * Componente reutilizable para mostrar una fila de información (Etiqueta: Valor).
 * (Esta función auxiliar estaba perfecta y la mantenemos).
 */
@Composable
private fun ProfileInfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            modifier = Modifier.width(100.dp)
        )
        Text(
            text = value,
            fontSize = 16.sp
        )
    }
}
