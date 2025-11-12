package com.example.app_definida

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.view.WindowCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.app_definida.navigation.AppRoute
import com.example.app_definida.navigation.NavigationEvent
import com.example.app_definida.repository.UserPreferencesRepository
import com.example.app_definida.ui.screens.MainScreen
import com.example.app_definida.ui.screens.PaymentScreen
import com.example.app_definida.ui.screens.PostScreen
import com.example.app_definida.ui.screens.RegistroScreen
import com.example.app_definida.ui.theme.APP_DEFINIDATheme
import com.example.app_definida.viewmodel.MainViewModel
import com.example.app_definida.viewmodel.UsuarioViewModel
import kotlinx.coroutines.flow.collectLatest


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window,false)

        setContent {
            APP_DEFINIDATheme {
                val postViewModel: com.example.app_definida.viewmodel.PostViewModel = viewModel()
                PostScreen(viewModel = postViewModel)
            }
        }
    }
}

@Composable
fun AppContent() {
    val navController = rememberNavController()
    val mainViewModel: MainViewModel = viewModel()

    val userPrefsRepository = UserPreferencesRepository(LocalContext.current)
    val sesionIniciada by userPrefsRepository.obtenerEstadoSesion().collectAsState(initial = null)

    LaunchedEffect(key1 = Unit) {
        mainViewModel.navEvents.collectLatest { event ->
            if (event is NavigationEvent.NavigateTo) {
                navController.navigate(event.appRoute.route) {
                    event.popUpToRoute?.let { popUpTo(it.route) { inclusive = event.inclusive } }
                }
            }
        }
    }

    if (sesionIniciada != null) {
        val startDestination = if (sesionIniciada == true) {
            AppRoute.Main.route
        } else {
            AppRoute.Registro.route
        }

        AppNavHost(
            navController = navController,
            mainViewModel = mainViewModel,
            startDestination = startDestination
        )
    } else {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }
}

@Composable
fun AppNavHost(
    navController: NavHostController,
    mainViewModel: MainViewModel,
    startDestination: String
) {
    val usuarioViewModel: UsuarioViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(AppRoute.Registro.route) {
            RegistroScreen(
                viewModel = usuarioViewModel,
                mainViewModel = mainViewModel
            )
        }

        composable(AppRoute.Main.route) {
            MainScreen(usuarioViewModel = usuarioViewModel)
        }

        composable(AppRoute.Payment.route) {
            PaymentScreen()
        }
    }
}


