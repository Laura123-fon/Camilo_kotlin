package com.example.app_definida.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.app_definida.viewmodel.UsuarioViewModel


@Composable
fun ProfileScreen(
    usuarioViewModel: UsuarioViewModel
) {
    val usuarioViewModel: UsuarioViewModel = viewModel()
    val estadoUsuario by usuarioViewModel.estado.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Default.Person,
            contentDescription = "Icono de Perfil",
            modifier = Modifier.size(100.dp),
            tint = Color(0xFF2E8B57) // Verde Esmeralda
        )
        Spacer(Modifier.height(16.dp))
        Text(
            text = "Mi Perfil",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.height(24.dp))

        ProfileInfoRow(label = "Nombre:", value = estadoUsuario.nombre)
        ProfileInfoRow(label = "Correo:", value = estadoUsuario.correo)
        ProfileInfoRow(label = "Dirección:", value = estadoUsuario.direccion)
    }
}

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
