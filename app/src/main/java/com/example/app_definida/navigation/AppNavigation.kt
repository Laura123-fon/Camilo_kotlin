package com.example.app_definida.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.app_definida.ui.screens.RegistroScreen
import com.example.app_definida.ui.screens.ResumenScreen
import com.example.app_definida.viewmodel.MainViewModel
import com.example.app_definida.viewmodel.UsuarioViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun AppNavigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val mainViewModel: MainViewModel = viewModel()
    val usuarioViewModel: UsuarioViewModel = viewModel()

    // Este LaunchedEffect escucha los eventos de navegación y los ejecuta.
    // Ya no debería darte ningún error aquí.
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
        startDestination = AppRoute.Registro.route, // <-- Usa AppRoute
        modifier = modifier
    ) {
        composable(AppRoute.Registro.route) { // <-- Usa AppRoute
            RegistroScreen(
                navController, usuarioViewModel,mainViewModel
            )
        }
        composable(AppRoute.Resumen.route) { // <-- Usa AppRoute
            ResumenScreen(
                usuarioViewModel
            )
        }
    }
}
