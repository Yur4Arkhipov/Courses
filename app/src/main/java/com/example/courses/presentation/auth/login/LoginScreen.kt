package com.example.courses.presentation.auth.login

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
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.courses.UiState
import com.example.courses.presentation.auth.AuthViewModel

@Composable
fun LoginScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: AuthViewModel = hiltViewModel()
) {
    val authState by viewModel.loginState.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = viewModel.email,
            onValueChange = { viewModel.email = it },
            label = { Text("Email") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

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
            modifier = Modifier.fillMaxWidth()
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