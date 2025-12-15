package com.example.app_definida.ui.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import com.example.app_definida.ui.profile.UsuarioViewModel

@Composable
fun ResumenScreen(
    usuarioViewModel: UsuarioViewModel,
    onNavigateToLogin: () -> Unit
) {
    val userProfile by usuarioViewModel.userProfile.collectAsState()

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

        Button(
            onClick = onNavigateToLogin,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Ir a Iniciar Sesión")
        }
    }
}
