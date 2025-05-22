package com.example.courses.domain.repository

import com.example.courses.data.model.CourseCardDto

interface FavoriteRepository {
    suspend fun addToFavorites(course: CourseCardDto)

    suspend fun removeFromFavorites(courseId: Int)

    suspend fun isFavorite(courseId: Int): Boolean
    suspend fun getAllFavorites(): List<CourseCardDto>
}