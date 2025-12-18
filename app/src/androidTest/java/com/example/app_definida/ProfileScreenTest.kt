package com.example.app_definida

import android.net.Uri
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.example.app_definida.data.model.UserProfile
import com.example.app_definida.ui.profile.ProfileScreen
import com.example.app_definida.ui.profile.UsuarioViewModel
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ProfileScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var mockUsuarioViewModel: UsuarioViewModel
    private val userProfileFlow = MutableStateFlow<UserProfile?>(null)
    private val uriImagenFlow = MutableStateFlow<Uri?>(null)

    @Before
    fun setup() {
        mockUsuarioViewModel = mockk(relaxed = true)
        every { mockUsuarioViewModel.userProfile } returns userProfileFlow
        every { mockUsuarioViewModel.uriImagen } returns uriImagenFlow

        composeTestRule.setContent {
            ProfileScreen(usuarioViewModel = mockUsuarioViewModel)
        }
    }

    @Test
    fun profileScreen_initialState_displaysButtons() {
        composeTestRule.onNodeWithText("Galería").assertIsDisplayed()
        composeTestRule.onNodeWithText("Cámara").assertIsDisplayed()
    }
}
