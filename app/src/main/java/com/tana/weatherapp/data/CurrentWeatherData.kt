package com.tana.weatherapp.data

import com.google.gson.annotations.SerializedName

data class CurrentWeatherData(
    val location: Location,
    val current: Current,
    @SerializedName("wind_kph")
    val wind: Int,
    val humidity: Int
)

data class Location(
    val name: String,
    @SerializedName("localtime")
    val localTime: String
)

data class Current(
    @SerializedName("temp_c")
    val temperature: Int,
    val condition: Condition
)

data class Condition(
    val text: String,
    val icon: String
)