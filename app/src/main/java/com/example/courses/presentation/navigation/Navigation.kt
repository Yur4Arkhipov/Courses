package com.example.courses.presentation.navigation

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.produceState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import androidx.navigation.toRoute
import com.example.courses.R
import com.example.courses.UiState
import com.example.courses.data.datastore.isOnboardingCompleted
import com.example.courses.data.datastore.setOnboardingCompleted
import com.example.courses.presentation.auth.login.LoginScreen
import com.example.courses.presentation.course_details.CourseDetailsScreen
import com.example.courses.presentation.favorites.FavoritesScreen
import com.example.courses.presentation.home.HomeScreen
import com.example.courses.presentation.home.HomeScreenViewModel
import com.example.courses.presentation.navigation.model.TopLevelRoute
import com.example.courses.presentation.onboarding.OnboardingScreen
import com.example.courses.presentation.profile.ProfileScreen
import androidx.navigation.compose.currentBackStackEntryAsState
import kotlinx.coroutines.launch


@Composable
fun Navigation(
    context: Context = LocalContext.current
) {

    val navController = rememberNavController()
    val onboardingCompletedState = produceState<Boolean?>(initialValue = null, context) {
        value = isOnboardingCompleted(context)
    }
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val showBottomBar = shouldShowBottomBar(navBackStackEntry.value)

    if (onboardingCompletedState.value == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    val startDestination = if (onboardingCompletedState.value == false) {
        SubLevelRoutes.Onboarding
    } else {
        SubLevelRoutes.Login
    }

    val topLevelRoutes = listOf(
        TopLevelRoute("Главная", TopLevelRoutes.Home, painterResource(R.drawable.ic_home_nav)),
        TopLevelRoute("Избранное", TopLevelRoutes.Favorites, painterResource(R.drawable.ic_bookmark)),
        TopLevelRoute("Аккаунт", TopLevelRoutes.Profile, painterResource(R.drawable.ic_person)),
    )

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                BottomNavigation(
                    navController = navController,
                    routes = topLevelRoutes
                )
            }
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable<SubLevelRoutes.Onboarding> {
                val coroutineScope = rememberCoroutineScope()

                OnboardingScreen(
                    onNextClick = {
                        coroutineScope.launch {
                            setOnboardingCompleted(context)
                            navController.navigate(
                                route = routeOf<SubLevelRoutes.Login>(),
                                navOptions = navOptions {
                                    popUpTo(routeOf<SubLevelRoutes.Onboarding>()) {
                                        inclusive = true
                                    }
                                }
                            )
                        }
                    }
                )
            }

            composable<SubLevelRoutes.Login> {
                LoginScreen(
                    navController = navController,
                    onLoginSuccess = {
                        navController.navigate(
                            route = routeOf<TopLevelRoutes.Home>(),
                            navOptions = navOptions {
                                popUpTo(routeOf<SubLevelRoutes.Login>()) {
                                    inclusive = true
                                }
                            }
                        )
                    }
                )
            }

            composable<TopLevelRoutes.Home> { HomeScreen(navController = navController, innerPadding = innerPadding) }
            composable<TopLevelRoutes.Favorites> { FavoritesScreen(navController = navController) }
            composable<TopLevelRoutes.Profile> { ProfileScreen() }
            composable<SubLevelRoutes.CourseDetails> { backStackEntry ->
                val courseId = backStackEntry.toRoute<SubLevelRoutes.CourseDetails>().courseId
                val viewModel = hiltViewModel<HomeScreenViewModel>()

                LaunchedEffect(courseId) {
                    viewModel.uiState.value.let { state ->
                        if (state is UiState.Success) {
                            state.data.find { it.id == courseId }?.let { course ->
                                viewModel.selectCourse(course)
                            }
                        }
                    }
                }

                CourseDetailsScreen(navController, viewModel)
            }
        }
    }
}

inline fun <reified T> routeOf(): String {
    return T::class.qualifiedName ?: error("Route not found for ${T::class}")
}

@Composable
fun shouldShowBottomBar(navBackStackEntry: NavBackStackEntry?): Boolean {
    val destination = navBackStackEntry?.destination?.route
    return destination in listOf(
        TopLevelRoutes.Home::class.qualifiedName,
        TopLevelRoutes.Favorites::class.qualifiedName,
        TopLevelRoutes.Profile::class.qualifiedName
    )
}

