package com.example.app_definida.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Clase sellada que define TODAS las rutas (pantallas) de la aplicación.
 * Este es el "mapa" central de tu app. MainActivity lo usa como referencia.
 */
sealed class AppRoute(
    val route: String,
    // Los siguientes parámetros son opcionales, pero útiles para barras de navegación.
    val title: String? = null,
    val icon: ImageVector? = null
) {
    // --- Rutas que usa el NavHost en MainActivity ---

    // 1. Usada por startDestination y RegistroScreen
    object Registro : AppRoute(route = "registro_screen")

    // 2. Usada por HomeScreen
    object Home : AppRoute(
        route = "home_screen",
        title = "Home",
        icon = Icons.Default.Home
    )

    // 3. Usada por ProfileScreen
    object Profile : AppRoute(
        route = "profile_screen",
        title = "Perfil",
        icon = Icons.Default.Person
    )

    // 4. Usada por ResumenScreen
    object Resumen : AppRoute(route = "resumen_screen")

    // Si descomentas la ruta "Settings" en MainActivity,
    // también debes descomentar esto:
    /*
    object Settings : AppRoute(
        route = "settings_screen",
        title = "Ajustes",
        icon = Icons.Default.Settings // Necesitarás importar Icons.Default.Settings
    )
    */
}
