package com.example.courses.domain.di

import android.content.Context
import androidx.room.Room
import com.example.courses.AppDatabase
import com.example.courses.data.local.FavoriteCourseDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "favorite_courses"
        ).build()
    }

    @Provides
    fun provideFavoriteCourseDao(appDatabase: AppDatabase): FavoriteCourseDao {
        return appDatabase.favoriteCourseDao()
    }
}
