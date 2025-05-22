package com.example.courses.presentation.auth.login

import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.courses.UiState
import com.example.courses.presentation.auth.AuthViewModel

@Composable
fun LoginScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: AuthViewModel = hiltViewModel(),
    onLoginSuccess: () -> Unit
) {
    val authState by viewModel.loginState.collectAsState()
    val context = LocalContext.current

    val emailRegex = Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}\$")

    val isEmailValid = emailRegex.matches(viewModel.email)
    val isFormValid = isEmailValid && viewModel.password.isNotBlank()

    LaunchedEffect(authState) {
        if (authState is UiState.Success) {
            onLoginSuccess()
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = viewModel.email,
            onValueChange = {
                val filtered = it.filter { ch -> ch.toInt() < 128 }
                viewModel.email = filtered
            },
            label = { Text("Email") },
            singleLine = true,
            isError = viewModel.email.isNotBlank() && !isEmailValid,
            modifier = Modifier.fillMaxWidth(),
        )
        if (viewModel.email.isNotBlank() && !isEmailValid) {
            Text(
                text = "Некорректный email",
                color = Color.Red,
                modifier = Modifier.align(Alignment.Start)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = viewModel.password,
            onValueChange = { viewModel.password = it },
            label = { Text("Password") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { viewModel.login(email = viewModel.email, password = viewModel.password) },
            modifier = Modifier.fillMaxWidth(),
            enabled = isFormValid
        ) {
            Text("Login")
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row {
                Text(text = "Нету аккаунта?")
                Text(
                    text = "Регистрация",
                    color = Color.Green
                )
            }
            Text(
                text = "Забыл пароль",
                color = Color.Green
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(
                text = "ВК",
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable {
                    val intent = Intent(Intent.ACTION_VIEW, "https://vk.com/".toUri())
                    context.startActivity(intent)
                }
            )
            Text(
                text = "Одноклассники",
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable {
                    val intent = Intent(Intent.ACTION_VIEW, "https://ok.ru/".toUri())
                    context.startActivity(intent)
                }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        when (authState) {
            is UiState.Loading -> CircularProgressIndicator()
            is UiState.Success -> Text("Login successful!", color = Color.Green)
            is UiState.Error -> Text(
                (authState as UiState.Error).message,
                color = Color.Red
            )
            else -> Unit
        }

        val errorState by viewModel.authError.collectAsState()
        Text(errorState.toString())
    }
}