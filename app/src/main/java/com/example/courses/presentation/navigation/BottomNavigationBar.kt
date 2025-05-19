package com.example.courses.presentation.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.courses.presentation.navigation.model.TopLevelRoute

@Composable
fun BottomNavigation(
    navController: NavController,
    routes: List<TopLevelRoute>
) {
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry.value?.destination
    val currentRoute = currentDestination?.route
    val currentRouteSimpleName = currentRoute?.substringAfterLast('.')

    NavigationBar {
        routes.forEach { route ->
            val routeSimpleName = route.route::class.simpleName ?: ""
            val isSelected = currentRouteSimpleName == routeSimpleName

            NavigationBarItem(
                icon = {
                    Icon(
                        painter = route.icon,
                        contentDescription = route.name,
                        tint = if (isSelected) MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                },
                selected = isSelected,
                onClick = {
                    navController.navigate(route.route) {
                        launchSingleTop = true
                        restoreState = true
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                    }
                },
                label = {
                    Text(
                        text = route.name,
                        color = if (isSelected) MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.onSurfaceVariant,
                        fontSize = 12.sp,
                        lineHeight = 16.sp,
                        letterSpacing = 0.5.sp
                    )
                }
            )
        }
    }
}