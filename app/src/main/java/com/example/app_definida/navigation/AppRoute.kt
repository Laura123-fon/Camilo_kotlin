package com.example.app_definida.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed class AppRoute(
    val route: String,
    val title: String? = null,
    val icon: ImageVector? = null
) {

    object Registro : AppRoute(route = "registro_screen")

    object Home : AppRoute(
        route = "home_screen",
        title = "Home",
        icon = Icons.Default.Home
    )

    object Profile : AppRoute(
        route = "profile_screen",
        title = "Perfil",
        icon = Icons.Default.Person
    )

    object Resumen : AppRoute(route = "resumen_screen")

    object Payment : AppRoute("payment_screen")
    object Web : AppRoute(route = "web_screen")
    object Main : AppRoute(route = "main_screen")
}
