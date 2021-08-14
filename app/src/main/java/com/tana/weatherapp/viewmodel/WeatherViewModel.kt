package com.tana.weatherapp.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import com.tana.weatherapp.data.*
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel() {

    val weatherData: MutableState<CurrentWeatherData> = mutableStateOf( CurrentWeatherData() )
    val dayForecast: MutableState<CurrentDayForecast> = mutableStateOf( CurrentDayForecast() )
    val forecasts: MutableState<Forecasts> = mutableStateOf(Forecasts())
    var loading = false
    var cardToggle = mutableStateOf(true)
    init {
        viewModelScope.launch {
            loading = true

            val currentWeatherDataResult = WeatherRepository.getCurrentWeather()
            weatherData.value = currentWeatherDataResult

            val dayForecastResult = WeatherRepository.getDayForeCast()
            dayForecast.value = dayForecastResult

            val forecastsResult = WeatherRepository.getForecasts()
            forecasts.value = forecastsResult

            loading = false
        }
    }
}