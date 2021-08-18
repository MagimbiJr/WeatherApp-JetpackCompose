package com.tana.weatherapp.viewmodel

import com.tana.weatherapp.data.*
import com.tana.weatherapp.retrofit.WeatherClient

object WeatherRepository {
    suspend fun getCurrentWeather(location: String): CurrentWeatherData {
        return WeatherClient.weatherService().getCurrentWeather(location = location)
    }

    suspend fun getDayForeCast(location: String) : CurrentDayForecast {
        return WeatherClient.weatherService().getDayForecast(location = location)
    }

    suspend fun getForecasts(location: String) : Forecasts {
        return WeatherClient.weatherService().getForecasts(location = location)
    }
}