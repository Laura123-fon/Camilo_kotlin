package com.example.app_definida.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_definida.data.model.Product
import com.example.app_definida.data.repository.ProductRepository
import com.example.app_definida.data.repository.UserPreferencesRepository
import com.example.app_definida.navigation.AppRoute
import com.example.app_definida.navigation.NavigationEvent
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    sealed class InitialState {
        object Loading : InitialState()
        data class Loaded(val startDestination: AppRoute) : InitialState()
    }

    private val productRepository = ProductRepository()
    private val userPrefsRepository = UserPreferencesRepository(application.applicationContext)

    private val _productos = MutableStateFlow<List<Product>>(emptyList())
    val productos = _productos.asStateFlow()

    private val _navEvents = MutableSharedFlow<NavigationEvent>()
    val navEvents = _navEvents.asSharedFlow()

    private val _initialState = MutableStateFlow<InitialState>(InitialState.Loading)
    val initialState = _initialState.asStateFlow()

    init {
        cargarProductos()
        checkearEstadoSesion()
    }

    private fun checkearEstadoSesion() {
        viewModelScope.launch {
            userPrefsRepository.obtenerEstadoSesion().first().let { sesionIniciada ->
                val startRoute = if (sesionIniciada) AppRoute.Main else AppRoute.Registro
                _initialState.value = InitialState.Loaded(startRoute)
            }
        }
    }

    private fun cargarProductos() {
        _productos.value = productRepository.getProductos()
    }

    fun onLoginExitoso() {
        viewModelScope.launch {
            userPrefsRepository.guardarEstadoSesion(true)
            navigateTo(
                route = AppRoute.Main,
                popUpToRoute = AppRoute.Registro,
                inclusive = true
            )
        }
    }

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
