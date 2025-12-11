package com.example.app_definida.data.remote

import com.example.app_definida.data.model.AuthRequest
import com.example.app_definida.data.model.AuthResponse
import com.example.app_definida.data.model.Product
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @POST("auth/login")
    suspend fun login(@Body authRequest: AuthRequest): Response<AuthResponse>

    @POST("auth/register")
    suspend fun register(@Body authRequest: AuthRequest): Response<Unit>

    @GET("api/products/getAllProducts")
    suspend fun getProducts(): Response<List<Product>>
}
