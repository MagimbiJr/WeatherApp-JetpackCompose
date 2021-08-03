package com.tana.weatherapp.retrofit

import com.tana.weatherapp.data.CurrentWeatherData
import retrofit2.Response
import retrofit2.http.GET

interface CurrentWeatherService {
    @GET("current.json?key=7537952d64fa4825b7584109210308&q=Arusha&aqi=no")
    suspend fun getCurrentWeather() : Response<CurrentWeatherData>
}