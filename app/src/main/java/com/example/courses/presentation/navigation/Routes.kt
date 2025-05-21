package com.example.courses.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class TopLevelRoutes() {
    @Serializable object Home : TopLevelRoutes()
    @Serializable object Favorites : TopLevelRoutes()
    @Serializable object Profile : TopLevelRoutes()
}

@Serializable
sealed class SubLevelRoutes() {
    @Serializable object CourseDetails : SubLevelRoutes()
}