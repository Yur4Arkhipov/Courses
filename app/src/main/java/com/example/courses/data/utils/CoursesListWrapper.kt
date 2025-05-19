package com.example.courses.data.utils

import com.example.courses.data.model.CourseCardDto
import kotlinx.serialization.Serializable

@Serializable
data class CoursesListWrapper(
    val courses: List<CourseCardDto>
)