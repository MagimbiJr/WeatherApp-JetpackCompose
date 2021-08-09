package com.tana.weatherapp.viewmodel

import com.tana.weatherapp.data.CurrentDayForecast
import com.tana.weatherapp.data.CurrentWeatherData
import com.tana.weatherapp.data.DayForecast
import com.tana.weatherapp.data.ForecastDay
import com.tana.weatherapp.retrofit.WeatherClient

object WeatherRepository {
    suspend fun getCurrentWeather(): CurrentWeatherData {
        return WeatherClient.weatherService().getCurrentWeather()
    }

    suspend fun getDayForeCast() : CurrentDayForecast {
        return WeatherClient.weatherService().getDayForecast()
    }
}