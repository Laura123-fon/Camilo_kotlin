package com.example.app_definida.remote

import com.example.app_definida.model.Post
import retrofit2.http.GET

interface ApiService {
    @GET("/posts")
    suspend fun getPost(): List<Post>
}