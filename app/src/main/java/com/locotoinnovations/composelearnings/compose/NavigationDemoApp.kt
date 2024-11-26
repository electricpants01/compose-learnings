package com.locotoinnovations.composelearnings.compose

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun NavigationDemoApp() {
    val navHost = rememberNavController()
    NavigationNavHost(navController = navHost)
}

@Composable
fun NavigationNavHost(
    navController: NavHostController,
) {
    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(route = Screen.Home.route) {
            HomeScreen {
                navController.navigate(Screen.Detail.createRoute(it))
            }
        }
        composable(
            route = Screen.Detail.route,
            arguments = Screen.Detail.navArguments
        ) {
            DetailScreen()
        }
    }
}