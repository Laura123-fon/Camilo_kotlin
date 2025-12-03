package com.example.app_definida.ui.screens

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.example.app_definida.data.UserProfile

import com.example.app_definida.viewmodel.*
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MainScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    // Mocks para todos los ViewModels y dependencias
    private lateinit var mockUsuarioViewModel: UsuarioViewModel
    private lateinit var mockMainViewModel: MainViewModel
    private lateinit var mockCartViewModel: CartViewModel
    private lateinit var mockProductViewModel: ProductViewModel
    private lateinit var mockAuthViewModel: AuthViewModel
    private lateinit var mockOnLogout: () -> Unit

    // Flows para controlar el estado de los ViewModels
    private val userProfileStateFlow = MutableStateFlow<UserProfile?>(null)
    private val productUiStateFlow = MutableStateFlow<ProductUiState>(ProductUiState.Success(emptyList()))
    private val cartUiStateFlow = MutableStateFlow(CartUiState())
    private val authStateFlow = MutableStateFlow<AuthState>(AuthState.Idle)

    @Before
    fun setUp() {
        // Inicializar todos los mocks
        mockUsuarioViewModel = mockk(relaxed = true) {
            every { userProfile } returns userProfileStateFlow
        }
        mockMainViewModel = mockk(relaxed = true)
        mockCartViewModel = mockk(relaxed = true) {
            every { uiState } returns cartUiStateFlow
        }
        mockProductViewModel = mockk(relaxed = true) {
            every { uiState } returns productUiStateFlow
        }
        mockAuthViewModel = mockk(relaxed = true) {
            every { authState } returns authStateFlow
        }
        mockOnLogout = mockk(relaxed = true)

        // Cargar el Composable con los mocks
        composeTestRule.setContent {
            MainScreen(
                usuarioViewModel = mockUsuarioViewModel,
                mainViewModel = mockMainViewModel,
                cartViewModel = mockCartViewModel,
                productViewModel = mockProductViewModel,
                authViewModel = mockAuthViewModel,
                onLogout = mockOnLogout
            )
        }
    }

    @Test
    fun mainScreen_initialState_displaysCorrectly() {
        // 1. Verificar que el título se muestra
        composeTestRule.onNodeWithText("HuertoHogar").assertIsDisplayed()

        // 2. Verificar que los 3 items de navegación existen
        composeTestRule.onNodeWithText("Catálogo").assertIsDisplayed()
        composeTestRule.onNodeWithText("Perfil").assertIsDisplayed()
        composeTestRule.onNodeWithText("Carrito").assertIsDisplayed()

        // 3. Verificar que "Catálogo" está seleccionado por defecto
        composeTestRule.onNodeWithText("Catálogo").assertIsSelected()
        composeTestRule.onNodeWithText("Perfil").assertIsNotSelected()
        composeTestRule.onNodeWithText("Carrito").assertIsNotSelected()
    }

    @Test
    fun bottomNavigation_clickingProfile_selectsProfileItem() {
        // Act: Hacer clic en el ítem "Perfil"
        composeTestRule.onNodeWithText("Perfil").performClick()

        // Assert: Verificar que "Perfil" está seleccionado
        composeTestRule.onNodeWithText("Perfil").assertIsSelected()

        // Y que los otros no lo están
        composeTestRule.onNodeWithText("Catálogo").assertIsNotSelected()
        composeTestRule.onNodeWithText("Carrito").assertIsNotSelected()
    }

    @Test
    fun bottomNavigation_clickingCart_selectsCartItem() {
        // Act: Hacer clic en el ítem "Carrito"
        composeTestRule.onNodeWithText("Carrito").performClick()

        // Assert: Verificar que "Carrito" está seleccionado
        composeTestRule.onNodeWithText("Carrito").assertIsSelected()

        // Y que los otros no lo están
        composeTestRule.onNodeWithText("Catálogo").assertIsNotSelected()
        composeTestRule.onNodeWithText("Perfil").assertIsNotSelected()
    }
}
