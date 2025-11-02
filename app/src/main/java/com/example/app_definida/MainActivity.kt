package com.example.app_definida

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.app_definida.navigation.AppRoute // Asegúrate que esté importado
import com.example.app_definida.navigation.NavigationEvent
import com.example.app_definida.ui.screens.HomeScreen
import com.example.app_definida.ui.screens.ProfileScreen
import com.example.app_definida.ui.screens.RegistroScreen
import com.example.app_definida.ui.screens.ResumenScreen
import com.example.app_definida.ui.theme.APP_DEFINIDATheme
import com.example.app_definida.viewmodel.MainViewModel
import com.example.app_definida.viewmodel.UsuarioViewModel
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
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
    val usuarioViewModel: UsuarioViewModel = viewModel()

    // Escucha los eventos de navegación del MainViewModel
    LaunchedEffect(Unit) {
        mainViewModel.navEvents.collectLatest { event ->
            handleNavigationEvent(event, navController)
        }
    }

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        AppNavHost(
            navController = navController,
            mainViewModel = mainViewModel,
            usuarioViewModel = usuarioViewModel,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

/**
 * Control centralizado de navegación
 */
private fun handleNavigationEvent(
    event: NavigationEvent,
    navController: NavController
) {
    when (event) {
        is NavigationEvent.NavigateTo -> {
            navController.navigate(event.appRoute.route) {
                event.popUpToRoute?.let { popUpToRoute ->
                    popUpTo(popUpToRoute.route) {
                        inclusive = event.inclusive
                    }
                }
                launchSingleTop = event.singleTop
            }
        }

        is NavigationEvent.NavigateUp -> navController.navigateUp()
        is NavigationEvent.PopBackStack -> navController.popBackStack()
    }
}

/**
 * Define todas las pantallas disponibles en la app.
 */
@Composable
fun AppNavHost(
    navController: NavHostController,
    mainViewModel: MainViewModel,
    usuarioViewModel: UsuarioViewModel,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        // CORRECCIÓN 1: El ".route" debe ir junto a "Registro"
        startDestination = AppRoute.Registro.route, // pantalla inicial
        modifier = modifier
    ) {
        composable(AppRoute.Registro.route) {
            RegistroScreen(
                navController = navController,
                viewModel = usuarioViewModel,
                mainViewModel = mainViewModel
            )
        }
        composable(AppRoute.Home.route) {
            HomeScreen(navController, mainViewModel)
        }
        composable(AppRoute.Profile.route) {
            ProfileScreen(navController, mainViewModel)
        }

        // CORRECCIÓN 2: La ruta "Settings" debe estar definida en AppRoute.kt.
        // Si no existe, la comento para evitar errores.
        /*
        composable(AppRoute.Settings.route) {
            // SettingsScreen(navController, mainViewModel)
        }
        */

        // CORRECCIÓN 3: Usa "AppRoute.Resumen.route" en lugar del string "resumen"
        composable(AppRoute.Resumen.route) {
            ResumenScreen(viewModel = usuarioViewModel)
        }
    }
}
