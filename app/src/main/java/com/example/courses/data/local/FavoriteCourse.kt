package com.example.courses.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_courses")
data class FavoriteCourse(
    @PrimaryKey val id: Int,
    val title: String,
    val text: String,
    val price: Int,
    val rate: String,
    val startDate: String,
    val hasLike: Boolean,
    val publishDate: String
)