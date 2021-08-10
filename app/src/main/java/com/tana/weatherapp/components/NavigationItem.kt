package com.tana.weatherapp.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Insights
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavigationItem(val route: String, val icon: ImageVector, val title: String) {
    object Home : NavigationItem("home", Icons.Default.Home, "Home")
    object Forecasts : NavigationItem("forecast", Icons.Default.Insights, "Forecasts")
    object Search : NavigationItem("search", Icons.Default.Search, "Search")
    object Settings : NavigationItem("settings", Icons.Default.Settings, "Settings")
}