package com.example.courses

import android.app.Application
import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.courses.data.local.FavoriteCourse
import com.example.courses.data.local.FavoriteCourseDao
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App: Application()

@Database(
    entities = [FavoriteCourse::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteCourseDao(): FavoriteCourseDao
}