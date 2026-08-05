package com.corevo.main.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.*
import com.corevo.main.AppContainer
import com.corevo.main.viewmodel.*

sealed class Screen(val route: String, val title: String, val icon: String) {
    object Home : Screen("home", "Feed", "📰")
    object Library : Screen("library", "Exercises", "🏋️")
    object Workouts : Screen("workouts", "Plans", "📋")
    object Chat : Screen("chat", "Chat", "💬")
    object Profile : Screen("profile", "Profile", "👤")
}

@Composable
fun MainNavigation(factory: ViewModelFactory, appContainer: AppContainer) {
    val navController = rememberNavController()
    val items = listOf(
        Screen.Home,
        Screen.Library,
        Screen.Workouts,
        Screen.Chat,
        Screen.Profile
    )

    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route
                items.forEach { screen ->
                    NavigationBarItem(
                        icon = { Text(screen.icon) },
                        label = { Text(screen.title) },
                        selected = currentRoute == screen.route,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                val vm: HomeViewModel = androidx.lifecycle.viewmodel.compose.viewModel(factory = factory)
                HomeScreen(viewModel = vm)
            }
            composable(Screen.Library.route) {
                val vm: LibraryViewModel = androidx.lifecycle.viewmodel.compose.viewModel(factory = factory)
                LibraryScreen(viewModel = vm)
            }
            composable(Screen.Workouts.route) {
                val vm: WorkoutsViewModel = androidx.lifecycle.viewmodel.compose.viewModel(factory = factory)
                WorkoutsScreen(viewModel = vm)
            }
            composable(Screen.Chat.route) {
                val vm: ChatViewModel = androidx.lifecycle.viewmodel.compose.viewModel(factory = factory)
                ChatScreen(viewModel = vm)
            }
            composable(Screen.Profile.route) {
                val vm: ProfileViewModel = androidx.lifecycle.viewmodel.compose.viewModel(factory = factory)
                ProfileScreen(viewModel = vm)
            }
        }
    }
}
