package com.tana.weatherapp.data
//
//import com.google.gson.annotations.SerializedName
//
//data class Forecasts(
//    val forecast: WeeklyForecast? = null
//)
//
//data class WeeklyForecast(
//    @SerializedName("forecastday")
//    val forecastDay: List<ForecastWeek> = listOf()
//)
//
//data class ForecastWeek(
//    @SerializedName("date_epoch")
//    val date: Long? = null,
//    val day: Day? = null
//)
//
//data class Day(
//    @SerializedName("maxtemp_c")
//    val maxTemp: Double? = null,
//    @SerializedName("mintemp_c")
//    val minTemp: Double? = null,
//    val condition: DayCondition? = null
//)
//
//data class DayCondition(
//    val icon: String? = null
//)