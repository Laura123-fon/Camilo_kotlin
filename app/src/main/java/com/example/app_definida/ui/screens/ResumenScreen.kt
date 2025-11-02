package com.example.app_definida.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.app_definida.viewmodel.UsuarioViewModel

@Composable
fun ResumenScreen(viewModel: UsuarioViewModel) {
    // 1. OBSERVA EL ESTADO: Usamos `collectAsState` para obtener los datos del ViewModel.
    //    Cualquier cambio en el estado se reflejará aquí automáticamente.
    val estado by viewModel.estado.collectAsState()

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

        Text(
            text = "Bienvenido/a, ${estado.nombre}",
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Hemos registrado tu correo:")
        Text(
            text = estado.correo,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Tu dirección de envío es:")
        Text(
            text = estado.direccion,
            fontWeight = FontWeight.SemiBold
        )
    }
}
