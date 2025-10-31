package com.example.app_definida

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import com.example.app_definida.ui.screens.HomeScreen
import com.example.app_definida.ui.screens.HomeScreenCompacta
import com.example.app_definida.ui.theme.APP_DEFINIDATheme
import com.example.app_definida.ui.utils.obtenerWindowSizeClass

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppContent()
        }
    }
}

@Composable
fun AppContent() {
    APP_DEFINIDATheme {
        val windowSizeClass = obtenerWindowSizeClass()
        when (windowSizeClass.widthSizeClass) {
            WindowWidthSizeClass.Compact -> {
                HomeScreenCompacta()
            }
            else -> {
                HomeScreen()
            }
        }
    }
}
