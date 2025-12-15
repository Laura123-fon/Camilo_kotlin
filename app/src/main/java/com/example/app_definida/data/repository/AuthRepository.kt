package com.example.app_definida.data.repository

import com.example.app_definida.data.local.TokenManager
import com.example.app_definida.data.local.UserManager
import com.example.app_definida.data.model.AuthRequest
import com.example.app_definida.data.model.ErrorResponse
import com.example.app_definida.data.remote.ApiService
import com.google.gson.Gson
import retrofit2.HttpException
import java.io.IOException

class AuthRepository(
    private val apiService: ApiService,
    private val tokenManager: TokenManager,
    private val userManager: UserManager
) {

    suspend fun login(authRequest: AuthRequest): Result<Unit> {
        return try {
            val response = apiService.login(authRequest)
            if (response.isSuccessful && response.body() != null) {
                val body = response.body()!!
                tokenManager.saveToken(body.token)
                // Guarda solo la información que tenemos en este punto (el email/username)
                userManager.saveUserProfile(
                    nombre = "", // Dejamos el nombre vacío para ser actualizado después
                    apellido = "", // Dejamos el apellido vacío para ser actualizado después
                    email = body.username
                )
                Result.success(Unit)
            } else {
                val errorBody = response.errorBody()?.string()
                val errorMessage = if (errorBody != null) {
                    try {
                        val errorResponse = Gson().fromJson(errorBody, ErrorResponse::class.java)
                        errorResponse.error ?: "Credenciales incorrectas"
                    } catch (e: Exception) { "Credenciales incorrectas" }
                } else { "Credenciales incorrectas" }
                Result.failure(Exception(errorMessage))
            }
        } catch (e: HttpException) {
            Result.failure(Exception("Error de red. Inténtalo más tarde."))
        } catch (e: IOException) {
            Result.failure(Exception("No se pudo conectar al servidor. Revisa tu conexión."))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun register(authRequest: AuthRequest, nombre: String, apellido: String): Result<Unit> {
        return try {
            val response = apiService.register(authRequest)
            if (response.isSuccessful) {
                userManager.saveUserProfile(nombre, apellido, authRequest.username)
                Result.success(Unit)
            } else {
                val errorBody = response.errorBody()?.string()
                val errorMessage = if (errorBody != null) {
                    try {
                        val errorResponse = Gson().fromJson(errorBody, ErrorResponse::class.java)
                        errorResponse.error ?: "Error en el registro"
                    } catch (e: Exception) { "Error desconocido en el registro" }
                } else { "Error en el registro" }
                Result.failure(Exception(errorMessage))
            }
        } catch (e: HttpException) {
            Result.failure(Exception("Error de red. Inténtalo más tarde."))
        } catch (e: IOException) {
            Result.failure(Exception("No se pudo conectar al servidor. Revisa tu conexión."))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun logout() {
        tokenManager.clearToken()
        userManager.clearUserProfile()
    }
}
