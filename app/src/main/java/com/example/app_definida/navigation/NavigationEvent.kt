package com.example.app_definida.navigation

sealed class NavigationEvent {
    data class NavigateTo(
        val appRoute: AppRoute,
        val popUpToRoute: AppRoute? = null,
        val inclusive: Boolean = false,
        val singleTop: Boolean = false
    ) : NavigationEvent()

    object NavigateUp : NavigationEvent()
    object PopBackStack : NavigationEvent()
    object NavigateToHome : NavigationEvent()
}
