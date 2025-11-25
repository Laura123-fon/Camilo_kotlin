package com.example.app_definida.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.app_definida.model.User
import com.example.app_definida.navigation.AppRoute
import com.example.app_definida.viewmodel.LoginViewModel
import com.example.app_definida.viewmodel.MainViewModel
import kotlinx.coroutines.launch

@Composable
fun RegistroScreen(
    mainViewModel: MainViewModel,
    loginViewModel: LoginViewModel
) {
    val scope = rememberCoroutineScope()
    var nombre by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var clave by remember { mutableStateOf("") }
    var direccion by remember { mutableStateOf("") }
    var aceptaTerminos by remember { mutableStateOf(false) }

    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre de usuario") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = correo,
            onValueChange = { correo = it },
            label = { Text("Correo") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = clave,
            onValueChange = { clave = it },
            label = { Text("Clave") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = direccion,
            onValueChange = { direccion = it },
            label = { Text("Dirección") },
            modifier = Modifier.fillMaxWidth()
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = aceptaTerminos,
                onCheckedChange = { aceptaTerminos = it }
            )
            Spacer(Modifier.width(8.dp))
            Text("Acepto los términos y condiciones")
        }

        Button(
            onClick = {
                // Podríamos añadir una validación simple aquí si es necesario
                if (nombre.isNotBlank() && correo.isNotBlank() && clave.isNotBlank() && aceptaTerminos) {
                    scope.launch {
                        val newUser = User(
                            username = nombre,
                            email = correo,
                            passwordHash = clave // Ahora usamos el estado local, que es consistente
                        )
                        loginViewModel.register(newUser)

                        mainViewModel.navigateTo(
                            route = AppRoute.Login,
                            popUpToRoute = AppRoute.Registro,
                            inclusive = true
                        )
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = nombre.isNotBlank() && correo.isNotBlank() && clave.isNotBlank() && aceptaTerminos
        ) {
            Text("Registrarse")
        }
    }
}
