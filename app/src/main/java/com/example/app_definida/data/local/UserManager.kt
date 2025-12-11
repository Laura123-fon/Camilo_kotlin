package com.example.app_definida.data.local

import android.content.Context
import android.content.SharedPreferences
import com.example.app_definida.data.model.UserProfile

class UserManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("user_profile_prefs", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_NOMBRE = "user_nombre"
        private const val KEY_APELLIDO = "user_apellido"
        private const val KEY_EMAIL = "user_email"
    }

    fun saveUserProfile(nombre: String, apellido: String, email: String) {
        prefs.edit().apply {
            putString(KEY_NOMBRE, nombre)
            putString(KEY_APELLIDO, apellido)
            putString(KEY_EMAIL, email)
            apply()
        }
    }

    fun getUserProfile(): UserProfile {
        return UserProfile(
            nombre = prefs.getString(KEY_NOMBRE, null),
            apellido = prefs.getString(KEY_APELLIDO, null),
            email = prefs.getString(KEY_EMAIL, null)
        )
    }

    fun clearUserProfile() {
        prefs.edit().clear().apply()
    }
}
