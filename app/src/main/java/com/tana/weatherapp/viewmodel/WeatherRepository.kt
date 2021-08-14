package com.tana.weatherapp.viewmodel

import com.tana.weatherapp.data.*
import com.tana.weatherapp.retrofit.WeatherClient

object WeatherRepository {
    suspend fun getCurrentWeather(): CurrentWeatherData {
        return WeatherClient.weatherService().getCurrentWeather()
    }

    suspend fun getDayForeCast() : CurrentDayForecast {
        return WeatherClient.weatherService().getDayForecast()
    }

    suspend fun getForecasts() : Forecasts {
        return WeatherClient.weatherService().getForecasts()
    }
}