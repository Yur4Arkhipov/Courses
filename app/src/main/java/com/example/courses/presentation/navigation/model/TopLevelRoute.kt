package com.example.courses.presentation.navigation.model

import androidx.compose.ui.graphics.painter.Painter
import com.example.courses.presentation.navigation.TopLevelRoutes

data class TopLevelRoute(
    val name: String,
    val route: TopLevelRoutes,
    val icon: Painter
)