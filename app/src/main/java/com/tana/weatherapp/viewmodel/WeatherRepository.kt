package com.tana.weatherapp.viewmodel

import com.tana.weatherapp.data.*
import com.tana.weatherapp.retrofit.WeatherClient

object WeatherRepository {
    //    suspend fun getCurrentWeather(location: String): CurrentWeatherData {
//        return WeatherClient.weatherService().getCurrentWeather(location = location)
//    }
    suspend fun getWeatherInfo(
        latitude: Double,
        longitude: Double,
        key: String
    ) : WeatherData {
        return WeatherClient.weatherService().getWeatherInfo(
            latitude = latitude,
            longitude = longitude,
            key = key
        )
    }

//    suspend fun getDayForeCast(location: String): CurrentDayForecast {
//        return WeatherClient.weatherService().getDayForecast(location = location)
//    }

//    suspend fun getForecasts(location: String): Forecasts {
//        return WeatherClient.weatherService().getForecasts(location = location)
//    }
}