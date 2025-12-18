package com.example.app_definida

import com.example.app_definida.ui.auth.AuthViewModel
import com.example.app_definida.ui.auth.AuthState
import io.mockk.mockk
import org.junit.Assert.assertTrue
import org.junit.Test

class AuthViewModelTest {

    @Test
    fun authViewModel_initialState_isIdle() {
        // Usamos mockk(relaxed = true) como en el ejemplo
        val mockRepo = mockk<com.example.app_definida.data.repository.AuthRepository>(relaxed = true)
        val viewModel = AuthViewModel(mockRepo)
        
        // El estado inicial debe ser Idle (no cargando)
        assertTrue(viewModel.authState.value is AuthState.Idle)
    }
}
