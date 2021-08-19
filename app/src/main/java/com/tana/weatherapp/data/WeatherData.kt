package com.tana.weatherapp.data

import com.google.gson.annotations.SerializedName

data class WeatherData(
    @SerializedName("lat")
    val latitude: Double? = null,
    @SerializedName("lon")
    val longitude: Double? = null,
    val current: Current? = null,
    val hourly: List<Hour> = listOf(),
    val daily: List<Days>
)

data class Current(
    @SerializedName("dt")
    val epochDate: Long? = null,
    @SerializedName("temp")
    val temperature: Double? = null,
    val humidity: Int? = null,
    @SerializedName("wind_speed")
    val windSpeed: Double? = null,
    val weather: List<Weather> = listOf()
)

data class Weather(
    val icon: String? = null
)

data class Hour(
    @SerializedName("dt")
    val hour: Long? = null,
    @SerializedName("temp")
    val temperature: Double? = null,
    val weather: List<Weather>
)

data class Days(
    @SerializedName("dt")
    val epochDate: Long? = null,
    @SerializedName("temp")
    val temperature: Temperature? = null,
    val weather: List<Weather> = listOf()
)

data class Temperature(
    val day: Double? = null
)