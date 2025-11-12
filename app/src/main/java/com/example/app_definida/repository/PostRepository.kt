package com.example.app_definida.repository

import com.example.app_definida.model.Post
import com.example.app_definida.remote.RetrofitInstance

class PostRepository {
    suspend fun getPosts(): List<Post>{
        return RetrofitInstance.api.getPost()
    }
}