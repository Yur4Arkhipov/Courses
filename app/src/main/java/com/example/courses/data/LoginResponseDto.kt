package com.example.courses.data

data class LoginResponseDto(
    val accessToken: String,
    val refreshToken: String,
    val id: String
)