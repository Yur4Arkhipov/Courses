package com.example.courses.presentation.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.courses.R
import com.example.courses.presentation.course_details.CourseDetailsScreen
import com.example.courses.presentation.favorites.FavoritesScreen
import com.example.courses.presentation.home.HomeScreen
import com.example.courses.presentation.navigation.model.TopLevelRoute
import com.example.courses.presentation.profile.ProfileScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()

    val topLevelRoutes = listOf(
        TopLevelRoute("Главная", TopLevelRoutes.Home, painterResource(R.drawable.ic_home_nav)),
        TopLevelRoute("Избранное", TopLevelRoutes.Favorites, painterResource(R.drawable.ic_bookmark)),
        TopLevelRoute("Аккаунт", TopLevelRoutes.Profile, painterResource(R.drawable.ic_person)),
    )

    Scaffold(
        bottomBar = {
            BottomNavigation(
                navController = navController,
                routes = topLevelRoutes
            )
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = TopLevelRoutes.Home,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable<TopLevelRoutes.Home> { HomeScreen(navController = navController, innerPadding = innerPadding) }
            composable<TopLevelRoutes.Favorites> { FavoritesScreen() }
            composable<TopLevelRoutes.Profile> { ProfileScreen() }
            composable<SubLevelRoutes.CourseDetails> { CourseDetailsScreen() }
        }
    }
}
