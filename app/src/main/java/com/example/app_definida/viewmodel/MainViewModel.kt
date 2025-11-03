package com.example.app_definida.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_definida.model.Producto
import com.example.app_definida.navigation.AppRoute
import com.example.app_definida.navigation.NavigationEvent
import com.example.app_definida.repository.ProductoRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val productoRepository = ProductoRepository()
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

    fun navigateTo(
        route: AppRoute,
        popUpToRoute: AppRoute? = null,
        inclusive: Boolean = false,
        singleTop: Boolean = false // Añadido para consistencia
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
