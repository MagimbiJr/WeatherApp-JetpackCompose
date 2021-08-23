package com.tana.weatherapp

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.tana.weatherapp.components.NavigationItem
import com.tana.weatherapp.components.WeatherBottomNav
//import com.tana.weatherapp.data.CurrentDayForecast
//import com.tana.weatherapp.data.CurrentWeatherData
import com.tana.weatherapp.data.WeatherData
import com.tana.weatherapp.screens.ForecastsScreen
//import com.tana.weatherapp.screens.ForecastsScreen
import com.tana.weatherapp.screens.SearchScreen
import com.tana.weatherapp.screens.SettingsScreen
import com.tana.weatherapp.screens.WeatherScreen
import com.tana.weatherapp.viewmodel.WeatherViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WeatherNavGraph(
    navHostController: NavHostController,
    //currentWeatherData: CurrentWeatherData,
    currentWeatherData: WeatherData,
    //dayForecast: CurrentDayForecast,
    //forecasts: Forecasts,
    forecasts: WeatherData,
    viewModel: WeatherViewModel
) {
    Scaffold(
        bottomBar = { WeatherBottomNav(navHostController = navHostController) }
    ) {
        NavHost(navController = navHostController, startDestination = NavigationItem.Home.route) {
            composable(NavigationItem.Home.route) {
                WeatherScreen(
                    currentWeather = currentWeatherData,
//                    dayForecast = currentWeatherData,
                    viewModel = viewModel,
                    navHostController = navHostController
                )
            }
            composable(NavigationItem.Forecasts.route) {
                ForecastsScreen(
                    //currentDayForecast = currentWeatherData,
                    forecast = currentWeatherData,
                    viewModel = viewModel
                )
            }
            composable(NavigationItem.Search.route) {
                SearchScreen()
            }
            composable(NavigationItem.Settings.route) {
                SettingsScreen()
            }
        }
    }
}