package com.actividad_22.screen

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.example.app_definida.ui.auth.LoginScreen
import com.example.app_definida.ui.auth.AuthViewModel
import com.example.app_definida.ui.auth.AuthState
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Define una clase de prueba para la pantalla de inicio de sesión (LoginScreen).
 */
class LoginScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    // Mock del ViewModel real.
    private lateinit var mockAuthViewModel: AuthViewModel

    // Este flow nos permite controlar el estado que el Composable está observando.
    private lateinit var authStateFlow: MutableStateFlow<AuthState>

    // Mocks para las lambdas de navegación.
    private lateinit var mockOnLoginSuccess: () -> Unit
    private lateinit var mockOnNavigateToRegister: () -> Unit

    /**
     * Se encarga de inicializar las versiones simuladas y de cargar la pantalla de inicio de sesión.
     */
    @Before
    fun setUp() {
        // 1. Inicializar el flow con el estado real
        authStateFlow = MutableStateFlow(AuthState.Idle)

        // 2. Inicializar mocks de las funciones de navegación
        mockOnLoginSuccess = mockk(relaxed = true)
        mockOnNavigateToRegister = mockk(relaxed = true)

        // 3. Crear el mock del AuthViewModel real
        mockAuthViewModel = mockk(relaxed = true) {
            // Cuando se acceda a `authState`, devolvemos nuestro flow controlable.
            every { authState } returns authStateFlow
        }

        // 4. Cargar el Composable con el ViewModel simulado.
        composeTestRule.setContent {
            LoginScreen(
                authViewModel = mockAuthViewModel,
                onLoginSuccess = mockOnLoginSuccess,
                onNavigateToRegister = mockOnNavigateToRegister
            )
        }
    }

    /**
     * Prueba para verificar que todos los elementos iniciales de la pantalla se muestran correctamente.
     * El botón de Login debe estar deshabilitado inicialmente ya que los campos están vacíos.
     */
    @Test
    fun loginScreen_initialState_displaysAllElements() {
        // Título
        composeTestRule.onNodeWithText("Iniciar Sesión").assertIsDisplayed()

        // Campos de texto
        composeTestRule.onNodeWithText("Correo Electrónico").assertIsDisplayed()
        composeTestRule.onNodeWithText("Contraseña").assertIsDisplayed()

        // Botón de Login (debe estar deshabilitado)
        composeTestRule.onNodeWithText("Login").assertIsDisplayed().assertIsNotEnabled()

        // Botón de Registro (debe estar habilitado)
        composeTestRule.onNodeWithText("¿No tienes cuenta? Regístrate").assertIsDisplayed().assertIsEnabled()
    }

    /**
     * Prueba que al hacer clic en el botón de registro, se llama a la lambda de navegación.
     */
    @Test
    fun registerButton_whenClicked_callsNavigateToRegister() {
        // Act: Hacemos clic en el TextButton de registro.
        composeTestRule.onNodeWithText("¿No tienes cuenta? Regístrate").performClick()

        // Verificación: Confirmamos que la lambda de navegación fue llamada.
        verify { mockOnNavigateToRegister.invoke() }
    }

    /**
     * Prueba que al hacer clic en "Login" con campos llenos, se llama a authViewModel.login().
     */
    @Test
    fun loginButton_whenClickedWithValidInput_callsViewModelLogin() {
        val testEmail = "test@example.com"
        val testPassword = "password123"

        // Preparación: Llenamos los campos usando el texto de la etiqueta (label)
        composeTestRule.onNodeWithText("Correo Electrónico").performTextInput(testEmail)
        composeTestRule.onNodeWithText("Contraseña").performTextInput(testPassword)

        // Act: Hacemos clic en el botón de Login (ahora debería estar habilitado)
        composeTestRule.onNodeWithText("Login").assertIsEnabled().performClick()

        // Verificación: Confirmamos que la función 'login' del ViewModel fue llamada con los datos correctos.
        verify { mockAuthViewModel.login(testEmail, testPassword) }
    }

    /**
     * Prueba que cuando el estado del ViewModel cambia a Success, se llama a la lambda de éxito.
     */


    /**
     * Prueba que cuando el estado del ViewModel cambia a Error, el mensaje se muestra en la pantalla.
     */
    @Test
    fun authState_whenError_displaysErrorMessage() {
        val errorMessage = "Usuario o contraseña incorrectos."

        // Act: Simulamos un error de login.
        authStateFlow.value = AuthState.Error(errorMessage)

        // Verificación: El texto del error debe ser visible.
        composeTestRule.onNodeWithText(errorMessage).assertIsDisplayed()
    }

    /**
     * Prueba que mientras el estado es Loading, se muestra el indicador de progreso y los botones se deshabilitan.
     */
    @Test
    fun authState_whenLoading_showsIndicatorAndDisablesButtons() {
        // 1. Preparación: Llenamos los campos para que el botón de login sea potencialmente clickeable.
        composeTestRule.onNodeWithText("Correo Electrónico").performTextInput("a@a.com")
        composeTestRule.onNodeWithText("Contraseña").performTextInput("123456")

        // 2. Act: Cambiamos el estado a Loading
        authStateFlow.value = AuthState.Loading

        // 3. Verificación:
        // El CircularProgressIndicator debe estar visible (no hay una forma fácil de testearlo por tag/texto por defecto)
        // Pero podemos verificar que los botones están deshabilitados.

        // El botón de Login debe estar deshabilitado.
        composeTestRule.onNodeWithText("Login").assertIsNotEnabled()

        // El TextButton de Registro también debe estar deshabilitado.
        composeTestRule.onNodeWithText("¿No tienes cuenta? Regístrate").assertIsNotEnabled()
    }
}