package com.example.courses.presentation.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.courses.UiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class OnboardingViewModel(): ViewModel() {

    private val _uiState = MutableStateFlow<UiState<OnboardingData>>(UiState.Idle)
    val uiState: StateFlow<UiState<OnboardingData>> = _uiState.asStateFlow()

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
        _uiState.value = UiState.Loading
        viewModelScope.launch {
            try {
                delay(2000)

                _uiState.value = UiState.Success(
                    OnboardingData(
                        courses = courses,
                        rotatedCourses = emptyMap()
                    )
                )
            } catch (e: Exception) {
                _uiState.value = UiState.Error("Download error: ${e.message}")
            }
        }
    }

    fun changeRotateState(course: String) {
        val currentState = _uiState.value
        if (currentState is UiState.Success) {
            val currentAngle = currentState.data.rotatedCourses[course] ?: 0f
            val randomAngle = if ((0..1).random() == 0) 30f else -30f
            val newAngle = if(currentAngle == 0f) randomAngle else 0f

            _uiState.value = currentState.copy(
                data = currentState.data.copy(
                    rotatedCourses = currentState.data.rotatedCourses + (course to newAngle)
                )
            )
        }
    }
}

data class OnboardingData(
    val courses: List<String> = emptyList(),
    val rotatedCourses: Map<String, Float> = emptyMap(),
)