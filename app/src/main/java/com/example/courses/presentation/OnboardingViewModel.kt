package com.example.courses.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class OnboardingViewModel(): ViewModel() {

    private val _uiState = MutableStateFlow(OnboardingUiState())
    val uiState: StateFlow<OnboardingUiState> = _uiState.asStateFlow()

    private val courses = listOf(
        "1С Администрирование", "RabbitMQ", "Трафик",
        "Контент маркетинг", "B2B маркетинг", "Google аналитика",
        "UX исследователь", "Веб-аналитика", "Big Data",
        "Геймдизайн", "Веб-дизайн", "Cinema 4D", "Промпт инжениринг",
        "Webflow", "Three.js", "Парсинг", "Python-разработка"
    )

    init {
        loadCourses()
    }

    private fun loadCourses() {
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            try {
                _uiState.update { currentState ->
                    currentState.copy(
                        courses = courses,
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        error = "Exception: ${e.message}",
                        isLoading = false
                    )
                }
            }
        }
    }

    fun changeRotateState(course: String) {
        _uiState.update { currentState ->
            val currentAngle = currentState.rotatedCourses[course] ?: 0f
            val randomAngle = if ((0..1).random() == 0) 30f else -30f
            val newAngle = if(currentAngle == 0f) randomAngle else 0f
            currentState.copy(
                rotatedCourses = currentState.rotatedCourses + (course to newAngle)
            )
        }
    }
}

data class OnboardingUiState(
    val courses: List<String> = emptyList(),
    val rotatedCourses: Map<String, Float> = emptyMap(),
    val isLoading: Boolean = false,
    val error: String? = null
)