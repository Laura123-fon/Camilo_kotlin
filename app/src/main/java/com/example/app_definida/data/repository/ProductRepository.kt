package com.example.app_definida.data.repository

import com.example.app_definida.data.model.Product
import com.example.app_definida.data.remote.ApiService

class ProductRepository(private val apiService: ApiService) {

    suspend fun getProducts(): Result<List<Product>> {
        return try {
            val response = apiService.getProducts()
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Error al cargar los productos: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
