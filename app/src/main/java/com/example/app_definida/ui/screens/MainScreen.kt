package com.example.app_definida.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.app_definida.navigation.AppRoute
import com.example.app_definida.viewmodel.*

sealed class BottomBarRoute(val route: String, val title: String, val icon: ImageVector) {
    object Home : BottomBarRoute("home", "Catálogo", Icons.Default.Home)
    object Profile : BottomBarRoute("profile", "Perfil", Icons.Default.Person)
    object Cart : BottomBarRoute("cart", "Carrito", Icons.Default.ShoppingCart)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    usuarioViewModel: UsuarioViewModel,
    mainViewModel: MainViewModel,
    cartViewModel: CartViewModel,
    productViewModel: ProductViewModel,
    authViewModel: AuthViewModel, // <-- Nuevo parámetro
    onLogout: () -> Unit // <-- Nuevo parámetro
) {
    val navController = rememberNavController()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("HuertoHogar") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF2E8B57), // VerdeEsmeralda
                    titleContentColor = Color.White
                )
            )
        },
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                val navItems = listOf(BottomBarRoute.Home, BottomBarRoute.Profile, BottomBarRoute.Cart)

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
                HomeScreen(cartViewModel = cartViewModel, productViewModel = productViewModel)
            }
            composable(BottomBarRoute.Profile.route) {
                // Pasar las nuevas dependencias a ProfileScreen
                ProfileScreen(
                    usuarioViewModel = usuarioViewModel,
                    authViewModel = authViewModel,
                    onLogout = onLogout
                )
            }
            composable(BottomBarRoute.Cart.route) {
                CartScreen(cartViewModel = cartViewModel, mainViewModel = mainViewModel)
            }
        }
    }
}
