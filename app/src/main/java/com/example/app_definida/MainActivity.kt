package com.example.app_definida

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent // Importación correcta
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.app_definida.navigation.AppNavigation
import com.example.app_definida.navigation.NavigationEvent
import com.example.app_definida.ui.theme.APP_DEFINIDATheme
import com.example.app_definida.viewmodel.MainViewModel
import kotlinx.coroutines.flow.collectLatest

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            APP_DEFINIDATheme {
                val mainViewModel: MainViewModel = viewModel()
                val navController = rememberNavController()

                // "Cerebro" de la navegación: Escucha eventos del ViewModel y actúa sobre el NavController.
                LaunchedEffect(Unit) {
                    mainViewModel.navigationEvents.collectLatest { event ->
                        when (event) {
                            is NavigationEvent.NavigateTo -> {
                                navController.navigate(event.route.route) {
                                    event.popUpToRoute?.let { popUpTo(it.route) { inclusive = event.inclusive } }
                                    launchSingleTop = event.singleTop
                                    restoreState = true
                                }
                            }
                            is NavigationEvent.PopBackStack -> {
                                navController.popBackStack()
                            }
                            is NavigationEvent.NavigateUp -> {
                                navController.navigateUp()
                            }
                        }
                    }
                }

                // Construcción de la UI
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        // Pasamos las instancias de control a nuestro grafo de navegación.
                        AppNavigation(
                            navController = navController,
                            mainViewModel = mainViewModel
                        )
                    }
                }
            }
        }
    }
}
