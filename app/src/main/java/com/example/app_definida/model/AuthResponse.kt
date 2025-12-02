package com.example.app_definida.model

data class AuthResponse(
    val token: String,
    val username: String,
    val role: String
)
