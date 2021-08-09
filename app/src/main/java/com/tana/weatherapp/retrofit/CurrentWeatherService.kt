package com.tana.weatherapp.retrofit

import com.tana.weatherapp.data.CurrentWeatherData
import com.tana.weatherapp.data.CurrentDayForecast
import com.tana.weatherapp.data.ForecastDay
import retrofit2.http.GET

interface CurrentWeatherService {
    @GET("current.json?key=7537952d64fa4825b7584109210308&q=Arusha&aqi=no")
    suspend fun getCurrentWeather() : CurrentWeatherData

    @GET("forecast.json?key=7537952d64fa4825b7584109210308&q=Arusha&days=1&aqi=yes&alerts=no")
    suspend fun getDayForecast() : CurrentDayForecast
}