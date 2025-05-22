package com.example.courses.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.courses.UiState
import com.example.courses.data.model.CourseCardDto
import com.example.courses.data.repository.CourseCardRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val repository: CourseCardRepositoryImpl
): ViewModel() {

    sealed class SortType {
        object ByDate: SortType()
        object ByPrice : SortType()
    }

    private val _sortType = MutableStateFlow<SortType>(SortType.ByDate)
    val sortType: StateFlow<SortType> = _sortType.asStateFlow()

    private val _uiState = MutableStateFlow<UiState<List<CourseCardDto>>>(UiState.Idle)
    val uiState: StateFlow<UiState<List<CourseCardDto>>> = _uiState.asStateFlow()

    private val _selectedCourse = MutableStateFlow<CourseCardDto?>(null)
    val selectedCourse: StateFlow<CourseCardDto?> = _selectedCourse.asStateFlow()

    init {
        loadCourses()
    }

    private fun loadCourses() {
        _uiState.value = UiState.Loading
        viewModelScope.launch {
//            delay(2000)
            try {
                val courses = repository.getCourseCards()
                _uiState.value = UiState.Success(courses)
                sortCourses()
            } catch (e: Exception) {
                _uiState.value = UiState.Error("Ошибка загрузки данных: ${e.message}")
            }
        }
    }

    fun setSortType(type: SortType) {
        _sortType.value = type
        sortCourses()
    }

    fun selectCourse(course: CourseCardDto) {
        _selectedCourse.value = course
    }

    private fun sortCourses() {
        when (val currentState = _uiState.value) {
            is UiState.Success -> {
                val sorted = when (_sortType.value) {
                    is SortType.ByDate -> currentState.data.sortedBy { it.startDate}
                    is SortType.ByPrice -> currentState.data.sortedBy { it.price }
                }
                _uiState.value = UiState.Success(sorted)
            }
            else -> {}
        }
    }
}