package com.example.app_definida.navigation

sealed class AppRoute(val route: String) {
    object Login : AppRoute("login_screen")
    object Web : AppRoute("web_screen")
    object Registro : AppRoute("registro_screen")
    object Main : AppRoute("main_screen")
    object Payment : AppRoute("payment_screen")
    object Resumen : AppRoute("resumen_screen")
    object Perfil : AppRoute("perfil_screen") // Nueva ruta
}