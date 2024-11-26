package com.locotoinnovations.composelearnings.compose

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class Screen(
    val route: String,
    val navArguments: List<NamedNavArgument>,
) {
    data object Home : Screen("home", emptyList())

    data object Detail : Screen(
        route = "homeDetail/{detailId}",
        navArguments = listOf(
            navArgument("detailId") {
                type = NavType.StringType
            }
        )
    ) {
        fun createRoute(id: String) = "homeDetail/${id}"
    }
}