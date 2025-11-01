package com.example.app_definida.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect // <-- IMPORTANTE: Añadir esta importación
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.app_definida.navigation.Screen
import com.example.app_definida.viewmodel.MainViewModel
import kotlinx.coroutines.flow.collectLatest // <-- IMPORTANTE: Añadir esta importación
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: MainViewModel = viewModel()
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    // 2. AÑADIR EL LAUNCHEDEFFECT PARA ESCUCHAR EVENTOS DE NAVEGACIÓN
    // Este bloque es la pieza clave que faltaba.
    // Se ejecuta una vez y se mantiene "escuchando" mientras HomeScreen esté en pantalla.
    LaunchedEffect(Unit) {
        viewModel.navigationEvents.collectLatest { event ->
            when (event) {
                is com.example.app_definida.navigation.NavigationEvent.NavigateTo -> {
                    navController.navigate(event.route.route)
                }
                com.example.app_definida.navigation.NavigationEvent.PopBackStack -> {
                    navController.popBackStack()
                }
                com.example.app_definida.navigation.NavigationEvent.NavigateUp -> {
                    navController.navigateUp()
                }
            }
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Text("Menu", modifier = Modifier.padding(16.dp))
                NavigationDrawerItem(
                    label = { Text("Ir a perfil") },
                    selected = false,
                    onClick = {
                        scope.launch { drawerState.close() }
                        // Ahora esto funcionará porque el LaunchedEffect lo capturará
                        viewModel.navigateTo(Screen.Profile)
                    }
                )
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Pantalla Home") },
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch { drawerState.open() }
                        }) {
                            Icon(
                                imageVector = Icons.Filled.Menu,
                                contentDescription = "Menu"
                            )
                        }
                    }
                )
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text("Bienvenido a la pantalla de inicio")
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = {
                    // Y esto también funcionará
                    viewModel.navigateTo(Screen.Settings)
                }) {
                    Text("Ir a Configuración")
                }
            }
        }
    }
}
