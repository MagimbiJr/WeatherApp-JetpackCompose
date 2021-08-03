package com.tana.weatherapp.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CurrentWeatherClient {
    private val url = "http://api.weatherapi.com/v1/"
    private val retrofit = Retrofit.Builder()
        .baseUrl(url).addConverterFactory(GsonConverterFactory.create()).build()
    val servise = retrofit.create(CurrentWeatherService::class.java)
}