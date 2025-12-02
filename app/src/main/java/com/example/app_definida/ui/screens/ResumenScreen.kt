package com.example.app_definida.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.app_definida.navigation.AppRoute
import com.example.app_definida.viewmodel.MainViewModel
import com.example.app_definida.viewmodel.UsuarioViewModel

@Composable
fun ResumenScreen(
    usuarioViewModel: UsuarioViewModel,
    mainViewModel: MainViewModel // Para la navegación
) {
    val userProfile by usuarioViewModel.userProfile.collectAsState()

    // Forzar la recarga de los datos del perfil cuando la pantalla aparece
    LaunchedEffect(key1 = Unit) {
        usuarioViewModel.loadUserProfile()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "¡Registro Exitoso!",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(24.dp))

        // Mostrar los datos desde el nuevo userProfile
        Text(
            text = "Bienvenido/a, ${userProfile?.nombre ?: ""}",
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Hemos registrado su correo:")
        Text(
            text = userProfile?.email ?: "No disponible",
            fontWeight = FontWeight.SemiBold
        )
        
        Spacer(modifier = Modifier.weight(1f))

        // Botón para ir al Login
        Button(
            onClick = { 
                mainViewModel.navigateTo(
                    route = AppRoute.Login, 
                    popUpToRoute = AppRoute.Resumen, // Salir de la pantalla de resumen
                    inclusive = true
                )
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Ir a Iniciar Sesión")
        }
    }
}