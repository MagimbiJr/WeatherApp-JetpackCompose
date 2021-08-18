package com.tana.weatherapp.retrofit

import com.tana.weatherapp.data.*
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrentWeatherService {
    @GET("current.json?key=7537952d64fa4825b7584109210308")
    suspend fun getCurrentWeather(
        @Query("q") location: String,
        @Query("aqi") aqi: String = "no"
    ) : CurrentWeatherData

    @GET("forecast.json?key=7537952d64fa4825b7584109210308")
    suspend fun getDayForecast(
        @Query("q") location: String,
        @Query("days") days: Int = 1,
        @Query("aqi") aqi: String = "yes",
        @Query("alerts") alerts: String = "yes"
    ) : CurrentDayForecast

    @GET("forecast.json?key=7537952d64fa4825b7584109210308")
    suspend fun getForecasts(
        @Query("q") location: String,
        @Query("days") days: Int = 10,
        @Query("aqi") aqi: String = "yes",
        @Query("alerts") alerts: String = "yes"
    ) : Forecasts
}