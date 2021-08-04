package com.tana.weatherapp.viewmodel

import com.tana.weatherapp.retrofit.CurrentWeatherClient

object WeatherRepository {
    suspend fun getCurrentWeather() {
        CurrentWeatherClient.service.getCurrentWeather()
    }
}