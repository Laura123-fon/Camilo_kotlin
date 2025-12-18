package com.example.app_definida.data.remote

import android.content.Context
import com.example.app_definida.data.local.TokenManager
import com.example.app_definida.data.remote.interceptor.AuthInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private const val BASE_URL = "http://10.0.2.2:9090/"
    private var apiService: ApiService? = null

    fun getApiService(context: Context): ApiService {
        return apiService ?: synchronized(this) {
            val instance = create(context)
            apiService = instance
            instance
        }
    }

    private fun create(context: Context): ApiService {
        val tokenManager = TokenManager(context)

        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(tokenManager))
            .addInterceptor(loggingInterceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
