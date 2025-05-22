package com.example.courses.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavoriteCourseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(course: FavoriteCourse)

    @Delete
    suspend fun delete(course: FavoriteCourse)

    @Query("SELECT * FROM favorite_courses WHERE id = :courseId")
    suspend fun getFavorite(courseId: Int): FavoriteCourse?

    @Query("SELECT * FROM favorite_courses")
    suspend fun getAllFavorites(): List<FavoriteCourse>

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_courses WHERE id = :courseId)")
    suspend fun isFavorite(courseId: Int): Boolean
}