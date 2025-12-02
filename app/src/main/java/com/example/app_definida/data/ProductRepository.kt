package com.example.app_definida.data

import com.example.app_definida.model.Product
import com.example.app_definida.remote.ApiService

class ProductRepository(private val apiService: ApiService) {

    suspend fun getProducts(): Result<List<Product>> {
        return try {
            val response = apiService.getProducts()
            if (response.isSuccessful && response.body() != null) {
                // ¡SOLUCIÓN! Devolvemos directamente los productos de la API,
                // ya que las URLs de las imágenes son correctas.
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Error al cargar los productos: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
