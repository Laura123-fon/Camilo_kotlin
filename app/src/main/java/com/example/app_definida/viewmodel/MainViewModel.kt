package com.example.app_definida.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_definida.model.Producto
import com.example.app_definida.navigation.AppRoute
import com.example.app_definida.navigation.NavigationEvent
import com.example.app_definida.repository.ProductoRepository
import com.example.app_definida.repository.UserPreferencesRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val productoRepository = ProductoRepository()
    private val userPrefsRepository = UserPreferencesRepository(application)

    private val _productos = MutableStateFlow<List<Producto>>(emptyList())
    val productos = _productos.asStateFlow()

    private val _navEvents = MutableSharedFlow<NavigationEvent>()
    val navEvents = _navEvents.asSharedFlow()

    init {
        cargarProductos()
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


