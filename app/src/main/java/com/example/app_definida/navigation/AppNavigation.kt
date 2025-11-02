package com.example.app_definida.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.app_definida.ui.screens.HomeScreen
import com.example.app_definida.ui.screens.ProfileScreen
import com.example.app_definida.ui.screens.RegistroScreen
import com.example.app_definida.ui.screens.ResumenScreen
import com.example.app_definida.ui.screens.SettingsScreen
import com.example.app_definida.viewmodel.MainViewModel
import com.example.app_definida.viewmodel.UsuarioViewModel

@Composable
fun AppNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    // 1. CAMBIO: Recibe el MainViewModel principal
    mainViewModel: MainViewModel
) {
    // 2. CAMBIO: El UsuarioViewModel se crea aquí para que sea compartido
    //    solo por las pantallas que lo necesitan (Registro y Resumen).
    val usuarioViewModel: UsuarioViewModel = viewModel()

    NavHost(
        navController = navController,
        // La ruta de inicio ya está definida en MainActivity, pero aquí se confirma.
        startDestination = Screen.Home.route,
        modifier = modifier
    ) {
        // Pantallas que usan el MainViewModel
        composable(Screen.Home.route) {
            HomeScreen(mainViewModel)
        }
        composable(Screen.Profile.route) {
            ProfileScreen(mainViewModel)
        }
        composable(Screen.Settings.route) {
            SettingsScreen(mainViewModel)
        }

        // 3. CAMBIO: Pantallas que usan ambos ViewModels para una mejor comunicación.
        //    La ruta "registro" ahora es parte de la clase Screen para mayor consistencia.
        composable(Screen.Registro.route) {
            RegistroScreen(
                mainViewModel = mainViewModel,
                usuarioViewModel = usuarioViewModel
            )
        }
        composable(Screen.Resumen.route) {
            ResumenScreen(
                mainViewModel = mainViewModel,
                usuarioViewModel = usuarioViewModel
            )
        }
    }
}
