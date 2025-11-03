package com.example.app_definida.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.app_definida.viewmodel.CartViewModel
import com.example.app_definida.viewmodel.MainViewModel
import com.example.app_definida.viewmodel.UsuarioViewModel

sealed class BottomBarRoute(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Home : BottomBarRoute("home", "Catálogo", Icons.Default.Home)
    object Profile : BottomBarRoute("profile", "Perfil", Icons.Default.Person)
    object Cart : BottomBarRoute("cart", "Carrito de compras", Icons.Default.ShoppingCart)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val mainViewModel: MainViewModel = viewModel()
    val cartViewModel: CartViewModel = viewModel()
    val navController = rememberNavController()
    val usuarioViewModel: UsuarioViewModel = viewModel()


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("HuertoHogar") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF2E8B57), // Verde Esmeralda
                    titleContentColor = Color.White
                )
            )
        },
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                val navItems = listOf(
                    BottomBarRoute.Home,
                    BottomBarRoute.Profile,
                    BottomBarRoute.Cart
                )

                navItems.forEach { screen ->
                    NavigationBarItem(
                        icon = { Icon(screen.icon, contentDescription = screen.title) },
                        label = { Text(screen.title) },
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController,
            startDestination = BottomBarRoute.Home.route,
            Modifier.padding(innerPadding)
        ) {
            composable(BottomBarRoute.Home.route) {
                HomeScreen(cartViewModel = cartViewModel)
            }
            composable(BottomBarRoute.Profile.route) {
                ProfileScreen(usuarioViewModel = usuarioViewModel)
            }
            composable(BottomBarRoute.Cart.route) {
                CartScreen(cartViewModel = cartViewModel, mainViewModel = mainViewModel)
            }

        }
    }
}