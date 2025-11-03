package com.example.app_definida.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.app_definida.ui.screens.MainScreen
import com.example.app_definida.ui.screens.RegistroScreen
import com.example.app_definida.ui.screens.ResumenScreen
import com.example.app_definida.ui.screens.WebScreen
import com.example.app_definida.viewmodel.UsuarioViewModel
import com.example.app_definida.viewmodel.MainViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun AppNavigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val mainViewModel: MainViewModel = viewModel()
    val usuarioViewModel: UsuarioViewModel = viewModel()

    LaunchedEffect(key1 = Unit) {
        mainViewModel.navEvents.collectLatest { event ->
            when (event) {
                is NavigationEvent.NavigateTo -> {
                    navController.navigate(event.appRoute.route) {
                        event.popUpToRoute?.let { popUpTo(it.route) { inclusive = event.inclusive } }
                        launchSingleTop = event.singleTop
                    }
                }
                is NavigationEvent.PopBackStack -> navController.popBackStack()
                is NavigationEvent.NavigateUp -> navController.navigateUp()
            }
        }
    }

    NavHost(
        navController = navController,
        startDestination = AppRoute.Web.route, // <-- antes era Registro
        modifier = modifier
    ) {
        // --- Pantalla Web de Bienvenida ---
        composable(AppRoute.Web.route) {
            WebScreen(
                onContinuar = {
                    mainViewModel.navigateTo(
                        route = AppRoute.Registro,
                        popUpToRoute = AppRoute.Web,
                        inclusive = true
                    )
                }
            )
        }

        // --- Pantalla de Registro ---
        composable(AppRoute.Registro.route) {
            RegistroScreen(
                viewModel = usuarioViewModel,
                mainViewModel = mainViewModel
            )
        }

        // --- Pantalla de Resumen ---
        composable(AppRoute.Resumen.route) {
            ResumenScreen()
        }

        // --- Pantalla Principal (con Bottom Nav) ---
        composable(AppRoute.Main.route) {
            MainScreen()
        }
    }

}
