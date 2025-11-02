package com.example.app_definida.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_definida.navigation.AppRoute
import com.example.app_definida.navigation.NavigationEvent
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val _navEvents = MutableSharedFlow<NavigationEvent>()
    val navEvents = _navEvents.asSharedFlow()

    fun navigateTo(
        route: AppRoute,
        popUpToRoute: AppRoute? = null,
        inclusive: Boolean = false,
        singleTop: Boolean = false
    ) {
        viewModelScope.launch {
            _navEvents.emit(
                NavigationEvent.NavigateTo(
                    appRoute = route,
                    popUpToRoute = popUpToRoute,
                    inclusive = inclusive,
                    singleTop = singleTop
                )
            )
        }
    }
}
