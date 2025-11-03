package com.example.app_definida

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.app_definida.navigation.AppRoute
import com.example.app_definida.navigation.NavigationEvent
import com.example.app_definida.ui.screens.MainScreen
import com.example.app_definida.ui.screens.RegistroScreen
import com.example.app_definida.ui.theme.APP_DEFINIDATheme
import com.example.app_definida.viewmodel.MainViewModel
import com.example.app_definida.viewmodel.UsuarioViewModel
import kotlinx.coroutines.flow.collectLatest

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            APP_DEFINIDATheme {
                AppContent()
            }
        }
    }
}

@Composable
fun AppContent() {
    val navController = rememberNavController()
    val mainViewModel: MainViewModel = viewModel()

    // ESTE ES EL CEREBRO QUE ESCUCHA LOS EVENTOS DE NAVEGACIÓN
    LaunchedEffect(key1 = Unit) {
        mainViewModel.navEvents.collectLatest { event ->
            if (event is NavigationEvent.NavigateTo) {
                navController.navigate(event.appRoute.route) {
                    event.popUpToRoute?.let { popUpTo(it.route) { inclusive = event.inclusive } }
                }
            }
        }
    }

    // El NavHost que define el mapa de la app
    AppNavHost(
        navController = navController,
        mainViewModel = mainViewModel
    )
}

@Composable
fun AppNavHost(navController: NavHostController, mainViewModel: MainViewModel) {
    val usuarioViewModel: UsuarioViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = AppRoute.Registro.route
    ) {
        // Pantalla de Registro
        composable(AppRoute.Registro.route) {
            // Llama a la versión correcta de RegistroScreen
            RegistroScreen(
                viewModel = usuarioViewModel,
                mainViewModel = mainViewModel
            )
        }

        // Pantalla Principal (con la Bottom Navigation Bar)
        // ESTA ES LA RUTA QUE FALTABA O ESTABA INCORRECTA
        composable(AppRoute.Main.route) {
            MainScreen()
        }
    }
}
