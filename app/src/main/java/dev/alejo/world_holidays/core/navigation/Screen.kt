package dev.alejo.world_holidays.core.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home_screen")
    object About : Screen("about_screen")
}