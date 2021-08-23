package com.tana.weatherapp.data
//
//import com.google.gson.annotations.SerializedName
//
//data class CurrentDayForecast(
//    val forecast: DayForecast? = null,
//)
//
//data class DayForecast(
//    @SerializedName("forecastday")
//    val forecastDay: List<ForecastDay>? = null
//)
//
//data class ForecastDay(
//    @SerializedName("date_epoch")
//    val date: Long? = null,
//    val hour: List<Hour>? = null
//)
//
//data class Hour(
//    @SerializedName("time_epoch")
//    val time: Long? = null,
//    @SerializedName("temp_c")
//    val temp: Double? = null,
//    val condition: HourCondition? = null
//)
//
//data class HourCondition(
//    val icon: String? = null
//)