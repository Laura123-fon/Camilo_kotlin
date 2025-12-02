package com.example.app_definida.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.app_definida.navigation.AppRoute
import com.example.app_definida.navigation.NavigationEvent
import com.example.app_definida.viewmodel.MainViewModel
import com.example.app_definida.viewmodel.UsuarioViewModel

@Composable
fun PerfilScreen(
    usuarioViewModel: UsuarioViewModel,
    mainViewModel: MainViewModel // Necesario para la navegación de cierre de sesión
) {
    val userProfile by usuarioViewModel.userProfile.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Mi Perfil",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(32.dp))

        // Mostramos los datos del perfil del usuario
        userProfile?.let {
            InfoRow(label = "Nombre:", value = it.nombre ?: "No disponible")
            InfoRow(label = "Apellido:", value = it.apellido ?: "No disponible")
            InfoRow(label = "Email:", value = it.email ?: "No disponible")
        }

        Spacer(modifier = Modifier.weight(1f)) // Empuja el botón hacia abajo

        Button(
            onClick = {
                // Lógica para cerrar sesión
                // mainViewModel.logout() // Necesitaremos añadir esta lógica
                // Por ahora, navegamos al Login
                mainViewModel.navigateTo(
                    route = AppRoute.Login,
                    popUpToRoute = AppRoute.Main, // Limpia la pila de navegación hasta Main
                    inclusive = true
                )
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Cerrar Sesión")
        }
    }
}

@Composable
private fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, fontWeight = FontWeight.Bold, fontSize = 16.sp)
        Text(text = value, fontSize = 16.sp)
    }
}
