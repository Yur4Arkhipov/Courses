package com.example.courses.presentation.favorites

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.courses.data.local.FavoriteCourse
import com.example.courses.data.model.CourseCardDto
import com.example.courses.data.repository.FavoritesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val favoritesRepository: FavoritesRepository
) : ViewModel() {
    private val _favoritesState = MutableStateFlow<Set<Int>>(emptySet())
    val favoritesState: StateFlow<Set<Int>> = _favoritesState.asStateFlow()

    init {
        loadFavorites()
    }

    private fun loadFavorites() {
        viewModelScope.launch {
            try {
                val favorites = favoritesRepository.getAllFavorites().map { it.id }.toSet()
                _favoritesState.value = favorites
            } catch (e: Exception) {
                Log.e("FavoritesViewModel", "Error loading favorites", e)
            }
        }
    }

    suspend fun toggleFavorite(course: CourseCardDto) {
        val isCurrentlyFavorite = favoritesRepository.isFavorite(course.id)
        if (isCurrentlyFavorite) {
            favoritesRepository.removeFromFavorites(course.id)
            _favoritesState.value = _favoritesState.value - course.id
        } else {
            favoritesRepository.addToFavorites(course)
            _favoritesState.value = _favoritesState.value + course.id
        }
    }

    suspend fun isFavorite(courseId: Int): Boolean {
        return favoritesRepository.isFavorite(courseId)
    }

    suspend fun getFavoriteCourses(): List<CourseCardDto> {
        return favoritesRepository.getAllFavorites()
    }
}

fun FavoriteCourse.toCourseCardDto(): CourseCardDto {
    return CourseCardDto(
        id = id,
        title = title,
        text = text,
        price = price,
        rate = rate,
        startDate = LocalDate.parse(startDate),
        hasLike = hasLike,
        publishDate = LocalDate.parse(publishDate)
    )
}

