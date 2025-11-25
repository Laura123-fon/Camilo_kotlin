package com.example.app_definida.navigation

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.app_definida.ui.screens.LoginScreen
import com.example.app_definida.viewmodel.LoginViewModel
import com.example.app_definida.viewmodel.LoginViewModelFactory
import com.example.app_definida.ui.screens.MainScreen
import com.example.app_definida.ui.screens.RegistroScreen
import com.example.app_definida.ui.screens.ResumenScreen
import com.example.app_definida.ui.screens.WebScreen
import com.example.app_definida.viewmodel.MainViewModel
import com.example.app_definida.viewmodel.UsuarioViewModel
import kotlinx.coroutines.flow.collectLatest


@Composable
fun AppNavigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val mainViewModel: MainViewModel = viewModel()
    val usuarioViewModel: UsuarioViewModel = viewModel()
    val application = LocalContext.current.applicationContext as Application
    val loginViewModel: LoginViewModel = viewModel(factory = LoginViewModelFactory(application))


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
                is NavigationEvent.NavigateToHome -> navController.navigate(AppRoute.Main.route) {
                    popUpTo(AppRoute.Login.route) { inclusive = true }
                }
            }
        }
    }

    NavHost(
        navController = navController,
        startDestination = AppRoute.Login.route,
        modifier = modifier
    ) {
        composable(AppRoute.Login.route) {
            LoginScreen(
                mainViewModel = mainViewModel,
                viewModel = loginViewModel,
                onLoginSuccess = { userId ->
                    // 1. Cargamos el usuario en el ViewModel de Usuario
                    usuarioViewModel.cargarUsuarioPorId(userId)
                    // 2. Navegamos a la pantalla principal
                    mainViewModel.navigateTo(AppRoute.Main, popUpToRoute = AppRoute.Login, inclusive = true)
                }
            )
        }

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

        composable(AppRoute.Registro.route) {
            RegistroScreen(
                mainViewModel = mainViewModel,
                loginViewModel = loginViewModel
            )
        }

        composable(AppRoute.Resumen.route) {
            ResumenScreen()
        }

        composable(AppRoute.Main.route) {
            MainScreen(
                usuarioViewModel = usuarioViewModel,
                mainViewModel = mainViewModel
            )
        }
    }

}
