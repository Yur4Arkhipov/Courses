package com.example.courses.domain.repository

import com.example.courses.data.model.CourseCardDto

interface CourseCardRepository {
    suspend fun getCourseCards(): List<CourseCardDto>
    suspend fun  getCourseCard(): CourseCardDto
}