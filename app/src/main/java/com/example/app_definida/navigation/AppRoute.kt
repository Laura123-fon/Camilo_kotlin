package com.example.app_definida.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed class AppRoute(val route: String
) {
    object Web : AppRoute("web_screen")
    object Registro : AppRoute("registro_screen")

    object Main : AppRoute("main_screen")
    object Payment : AppRoute("payment_screen")

    object Resumen : AppRoute("resumen_screen")
}