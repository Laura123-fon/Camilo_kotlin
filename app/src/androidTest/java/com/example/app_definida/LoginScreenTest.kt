package com.example.app_definida

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.example.app_definida.ui.auth.LoginScreen
import com.example.app_definida.ui.auth.AuthViewModel
import com.example.app_definida.ui.auth.AuthState
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class LoginScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val mockAuthViewModel: AuthViewModel = mockk(relaxed = true)
    private val authStateFlow = MutableStateFlow<AuthState>(AuthState.Idle)

    @Before
    fun setup() {
        // Obligatorio: Mock del StateFlow para que Compose pueda leerlo
        every { mockAuthViewModel.authState } returns authStateFlow
        
        composeTestRule.setContent {
            LoginScreen(
                authViewModel = mockAuthViewModel,
                onLoginSuccess = {},
                onNavigateToRegister = {}
            )
        }
    }

    @Test
    fun loginScreen_displaysElements() {
        composeTestRule.onNodeWithText("Iniciar Sesión").assertIsDisplayed()
        composeTestRule.onNodeWithText("Login").assertIsDisplayed()
    }
}
