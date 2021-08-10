package com.tana.weatherapp.components

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.tana.weatherapp.ui.theme.SecondaryTextColor

@Composable
fun WeatherBottomNav(
    navHostController: NavHostController
) {
    val items = listOf(
        NavigationItem.Home,
        NavigationItem.Forecasts,
        NavigationItem.Search,
        NavigationItem.Settings
    )
    BottomNavigation(
        backgroundColor = MaterialTheme.colors.background
    ) {
        val navBackStackEntry by navHostController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach { item ->
            BottomNavigationItem(
                selected = currentRoute == item.route,
                onClick = {
                    navHostController.navigate(item.route) {
                        navHostController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route = route) { saveState = true }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = { Icon(imageVector = item.icon, contentDescription = item.title) },
                label = { Text(text = item.title) },
                alwaysShowLabel = false,
                selectedContentColor = MaterialTheme.colors.onBackground,
                unselectedContentColor = SecondaryTextColor
            )
        }
    }
}