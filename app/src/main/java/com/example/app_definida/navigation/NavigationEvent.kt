package com.example.app_definida.navigation

sealed class NavigationEvent {
    data class NavigateTo(
        val appRoute: AppRoute,
        val popUpToRoute: AppRoute? = null,
        val inclusive: Boolean = false,
        val singleTop: Boolean = true
    ) : NavigationEvent()

    data object PopBackStack : NavigationEvent()
    data object NavigateUp : NavigationEvent()
}
/**
solo commit */