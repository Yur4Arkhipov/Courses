package com.example.courses.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.courses.UiState
import com.example.courses.data.model.CourseCardDto
import com.example.courses.data.repository.CourseCardRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val repository: CourseCardRepositoryImpl
): ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<CourseCardDto>>>(UiState.Idle)
    val uiState: StateFlow<UiState<List<CourseCardDto>>> = _uiState.asStateFlow()

    init {
        loadCourses()
    }

    private fun loadCourses() {
        _uiState.value = UiState.Loading
        viewModelScope.launch {
            delay(2000)
            try {
                val courses = repository.getCourseCards()
                _uiState.value = UiState.Success(courses)
            } catch (e: Exception) {
                _uiState.value = UiState.Error("Ошибка загрузки данных: ${e.message}")
            }
        }
    }
}