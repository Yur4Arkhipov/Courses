package com.example.courses.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class CourseCard(
    val id: Int,
    val title: String,
    val text: String,
    val price: String,
    val rate: String,
    val startDate: String,
    val hasLike: Boolean,
    val publishDate: String
)
