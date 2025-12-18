package com.example.app_definida

import com.example.app_definida.data.repository.ProductRepository
import com.example.app_definida.ui.product.ProductUiState
import com.example.app_definida.ui.product.ProductViewModel
import io.mockk.mockk
import org.junit.Assert.assertTrue
import org.junit.Test

class ProductViewModelTest {

    @Test
    fun productViewModel_initialState_isLoading() {
        // Mock del repositorio como en el ejemplo
        val mockRepo = mockk<ProductRepository>(relaxed = true)
        val viewModel = ProductViewModel(mockRepo)
        
        // El catálogo debe iniciar cargando productos
        assertTrue(viewModel.uiState.value is ProductUiState.Loading)
    }
}
