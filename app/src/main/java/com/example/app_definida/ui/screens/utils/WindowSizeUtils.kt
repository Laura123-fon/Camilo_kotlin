// <-- CAMBIO: El package debe coincidir con tu árbol de carpetas
package com.example.app_definida.ui.utils

import android.app.Activity
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

/**
 * Calcula y devuelve la clase de tamaño de la ventana actual.
 * Esta función es esencial para crear diseños adaptativos que funcionen en diferentes
 * tamaños de pantalla (móviles, tabletas, etc.).
 */
@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun obtenerWindowSizeClass(): WindowSizeClass {
    // Obtenemos el contexto actual de la actividad
    val activity = LocalContext.current as Activity
    // Calculamos y devolvemos la clase de tamaño de la ventana
    return calculateWindowSizeClass(activity)
}
