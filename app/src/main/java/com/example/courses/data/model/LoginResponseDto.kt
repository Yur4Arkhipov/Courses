package com.example.courses.data.model

data class LoginResponseDto(
    val accessToken: String,
    val refreshToken: String,
    val id: String
)