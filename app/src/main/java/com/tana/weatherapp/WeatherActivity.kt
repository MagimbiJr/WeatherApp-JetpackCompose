package com.tana.weatherapp

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.tana.weatherapp.components.WeatherBottomNav
import com.tana.weatherapp.screens.WeatherScreen
import com.tana.weatherapp.ui.theme.CustomBackgroundColor
import com.tana.weatherapp.ui.theme.CustomSurfaceBackground
import com.tana.weatherapp.ui.theme.WeatherAppTheme
import com.tana.weatherapp.ui.theme.WeatherCustomTheme
import com.tana.weatherapp.viewmodel.WeatherViewModel

class MainActivity : ComponentActivity() {
    private val TAG = "MainActivity"
    private val viewModel by lazy {
        ViewModelProvider(this@MainActivity)
            .get(WeatherViewModel::class.java)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val weather = viewModel.weatherData.value
            val dayForecast = viewModel.dayForecast.value
            val navHostController = rememberNavController()
            val systemUiController = rememberSystemUiController()
            systemUiController.setSystemBarsColor(CustomBackgroundColor)
            systemUiController.setStatusBarColor(CustomSurfaceBackground)
            WeatherCustomTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    WeatherNavGraph(
                        navHostController = navHostController,
                        currentWeatherData = weather,
                        dayForecast = dayForecast,
                        viewModel = viewModel
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    WeatherAppTheme {
        //WeatherImage("Android")
    }
}