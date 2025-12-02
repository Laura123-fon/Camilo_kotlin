package com.example.app_definida.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_definida.data.ProductRepository
import com.example.app_definida.model.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

// Estado para la UI de la pantalla de productos
sealed class ProductUiState {
    object Loading : ProductUiState()
    data class Success(val products: List<Product>) : ProductUiState()
    data class Error(val message: String) : ProductUiState()
}

class ProductViewModel(private val productRepository: ProductRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<ProductUiState>(ProductUiState.Loading)
    val uiState: StateFlow<ProductUiState> = _uiState

    init {
        fetchProducts()
    }

    fun fetchProducts() {
        viewModelScope.launch {
            _uiState.value = ProductUiState.Loading
            val result = productRepository.getProducts()
            _uiState.value = if (result.isSuccess) {
                ProductUiState.Success(result.getOrNull() ?: emptyList())
            } else {
                ProductUiState.Error(result.exceptionOrNull()?.message ?: "Error desconocido")
            }
        }
    }
}
