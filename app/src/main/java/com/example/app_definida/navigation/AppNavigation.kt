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

    // 2. El "cerebro" de la navegación que escucha los eventos del MainViewModel.
    LaunchedEffect(key1 = Unit) {
        mainViewModel.navigationEvents.collectLatest { event ->
            when (event) {
                is NavigationEvent.NavigateTo -> {
                    navController.navigate(event.route.route) {
                        event.popUpToRoute?.let { popUpTo(it.route) { inclusive = event.inclusive } }
                        launchSingleTop = event.singleTop
                        restoreState = true
                    }
                }
                is NavigationEvent.PopBackStack -> navController.popBackStack()
                is NavigationEvent.NavigateUp -> navController.navigateUp()
            }
        }
    }

    // 3. El NavHost usa los controladores que se acaban de crear en este Composable.
    NavHost(
        navController = navController,
        startDestination = Screen.Registro.route,
        modifier = modifier
    ) {
        composable(Screen.Registro.route) {
            RegistroScreen(
                navController, usuarioViewModel
            )
        }
        composable(Screen.Resumen.route) {
            ResumenScreen(
                usuarioViewModel
            )
        }
    }
}
