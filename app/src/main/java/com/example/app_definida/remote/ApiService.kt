package com.example.app_definida.remote

import com.example.app_definida.model.AuthRequest
import com.example.app_definida.model.AuthResponse
import com.example.app_definida.model.Product
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    // --- Auth Endpoints ---
    @POST("auth/login")
    suspend fun login(@Body authRequest: AuthRequest): Response<AuthResponse>

    @POST("auth/register")
    suspend fun register(@Body authRequest: AuthRequest): Response<Unit>

    // --- Product Endpoints ---
    @GET("api/products/getAllProducts")
    suspend fun getProducts(): Response<List<Product>>
}
