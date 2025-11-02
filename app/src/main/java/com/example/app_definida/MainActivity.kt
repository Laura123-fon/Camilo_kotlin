package com.example.app_definida

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
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
        enableEdgeToEdge()
        setContent {
            APP_DEFINIDATheme {
                // 1. Creas el ViewModel y el NavController (¡Correcto!)
                val mainViewModel: MainViewModel = viewModel()
                val navController = rememberNavController()

                // 2. El "cerebro" de la navegación que escucha eventos (¡Correcto!)
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

                // 3. Construcción de la UI
                Scaffold { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        AppNavigation()
                    }
                }
            }
        }
    }
}
