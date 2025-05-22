package com.example.courses.presentation.favorites

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.courses.data.model.CourseCardDto
import kotlinx.coroutines.launch
import androidx.compose.foundation.lazy.items
import com.example.courses.presentation.home.CourseCard

@Composable
fun FavoritesScreen(
    navController: NavController,
    viewModel: FavoritesViewModel = hiltViewModel()
) {
    val coroutineScope = rememberCoroutineScope()
    val favoriteCourses = remember { mutableStateOf<List<CourseCardDto>>(emptyList()) }

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            favoriteCourses.value = viewModel.getFavoriteCourses()
        }
    }

    val onToggleFavorite: (CourseCardDto) -> Unit = { course ->
        coroutineScope.launch {
            viewModel.toggleFavorite(course)
            favoriteCourses.value = viewModel.getFavoriteCourses()
        }
    }

    if (favoriteCourses.value.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Нет избранных курсов")
        }
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            items(favoriteCourses.value) { course ->
                CourseCard(
                    course = course,
                    navController = navController,
                    onToggleFavorite = onToggleFavorite
                )
            }
        }
    }
}


