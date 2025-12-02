package com.example.app_definida.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_definida.model.Producto
import com.example.app_definida.navigation.AppRoute
import com.example.app_definida.navigation.NavigationEvent
import com.example.app_definida.repository.ProductoRepository
import com.example.app_definida.repository.UserPreferencesRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

// 1. Creamos un estado para la pantalla de carga inicial
sealed class InitialState {
    object Loading : InitialState()
    data class Loaded(val startDestination: AppRoute) : InitialState()
}

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val productoRepository = ProductoRepository()
    private val userPrefsRepository = UserPreferencesRepository(application.applicationContext)

    private val _productos = MutableStateFlow<List<Producto>>(emptyList())
    val productos = _productos.asStateFlow()

    private val _navEvents = MutableSharedFlow<NavigationEvent>()
    val navEvents = _navEvents.asSharedFlow()

    // 2. Creamos un StateFlow para el estado inicial
    private val _initialState = MutableStateFlow<InitialState>(InitialState.Loading)
    val initialState = _initialState.asStateFlow()

    init {
        cargarProductos()
        checkearEstadoSesion()
    }

    // 3. Función que comprueba la sesión y actualiza el estado inicial
    private fun checkearEstadoSesion() {
        viewModelScope.launch {
            userPrefsRepository.obtenerEstadoSesion().first().let { sesionIniciada ->
                val startRoute = if (sesionIniciada) AppRoute.Main else AppRoute.Registro
                _initialState.value = InitialState.Loaded(startRoute)
            }
        }
    }

    private fun cargarProductos() {
        _productos.value = productoRepository.getProductos()
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
