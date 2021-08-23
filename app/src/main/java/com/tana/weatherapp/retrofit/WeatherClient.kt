package com.tana.weatherapp.retrofit

import com.tana.weatherapp.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object WeatherClient {

    fun weatherService(): CurrentWeatherService {
        return Retrofit.Builder()
            //.baseUrl("https://api.weatherapi.com/v1/")
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(CurrentWeatherService::class.java)
    }
}