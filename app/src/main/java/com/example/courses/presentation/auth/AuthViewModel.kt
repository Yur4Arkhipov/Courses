package com.example.courses.presentation.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.courses.UiState
import com.example.courses.data.LoginResponseDto
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class AuthViewModel @Inject constructor(
//    private val loginUseCase: LoginUseCase,
): ViewModel() {

    private val _loginState = MutableStateFlow<UiState<LoginResponseDto>>(UiState.Idle)
    val loginState: StateFlow<UiState<LoginResponseDto>> get() = _loginState

    /**
     * Здесь можно добавить состояния для обработки ошибок, для регистрации, аунтефикации
     */
    private val _authError = MutableStateFlow<String?>(null)
    val authError: StateFlow<String?> = _authError

    var email by mutableStateOf("")
    var password by mutableStateOf("")

    /**
     * Здесь может быть блок инициализации, проверяющий аутентифицирован ли пользователь,
     * сохранение access, refresh токенов, чтобы пользователю не приходилось постоянно при каждом
     * входе логиниться
     */

    /**
     * Здесь происходит фейковая авторизация для упрощения реализации,
     * сама реализация должна происходить через loginUseCase()
     */
    fun login(email: String, password: String) {
        _loginState.value = UiState.Loading
        _authError.value = null

        viewModelScope.launch {
            try {
                delay(2000)

                if (email == "example@email.com" && password == "123123") {
                    val fakeLoginResponse = LoginResponseDto(
                        accessToken = "fake",
                        refreshToken = "fake",
                        id = "1"
                    )
                    _loginState.value = UiState.Success(fakeLoginResponse)
                } else {
                    throw Exception("Неверный логин или пароль")
                }
            } catch (e: Exception) {
                _loginState.value = UiState.Error(e.toString())
                _authError.value = e.message
            }
        }
    }

}