package com.example.app_definida.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "huerto_hogar_prefs")
class UserPreferencesRepository(private val context: Context) {

    private val SESION_INICIADA = booleanPreferencesKey("sesion_iniciada")

    suspend fun guardarEstadoSesion(sesionIniciada: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[SESION_INICIADA] = sesionIniciada
        }
    }

    fun obtenerEstadoSesion(): Flow<Boolean> {
        return context.dataStore.data.map { preferences ->
            preferences[SESION_INICIADA] ?: false
        }
    }
}
