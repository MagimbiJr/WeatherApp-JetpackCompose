package com.tana.weatherapp.viewmodel

import android.Manifest
import android.app.Application
import android.content.pm.PackageManager
import android.location.Location
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.core.app.ActivityCompat
import androidx.lifecycle.*
import com.tana.weatherapp.API_KEY
import com.tana.weatherapp.data.*
import kotlinx.coroutines.launch

class WeatherViewModel(application: Application) : AndroidViewModel(application) {

    //val weatherData: MutableState<CurrentWeatherData> = mutableStateOf(CurrentWeatherData())
    val weatherData: MutableState<WeatherData> = mutableStateOf(WeatherData())
    //val dayForecast: MutableState<CurrentDayForecast> = mutableStateOf(CurrentDayForecast())
    //val forecasts: MutableState<Forecasts> = mutableStateOf(Forecasts())
    var loading = false
    var cardToggle = mutableStateOf(true)
    val locationRepository = LocationRepository(context = application)
    var isBadResult = false


    init {
        loading = true

        if (ActivityCompat.checkSelfPermission(application,
            Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationRepository.fusedLocationClient.lastLocation.addOnSuccessListener { location: Location ->
//                val location = locationRepository.deviceLocation.getDeviceLocation(
//                    location.latitude,
//                    location.longitude,
//                    geocoder
//                )

                viewModelScope.launch {

                    try {
                        val weatherDataResult =
                            WeatherRepository.getWeatherInfo(
                                locationRepository.latitude,
                                locationRepository.longitude,
                                API_KEY
                            )
                        weatherData.value = weatherDataResult
                        loading = false
                    } catch (e: Exception) {
                        isBadResult = true
                        loading = false
                        Toast.makeText(application, e.localizedMessage, Toast.LENGTH_LONG).show()

                    }

//                    val currentWeatherDataResult =
//                        WeatherRepository.getCurrentWeather(location = location)
//                    weatherData.value = currentWeatherDataResult

//                    val dayForecastResult = WeatherRepository.getDayForeCast(location = location)
//                    dayForecast.value = dayForecastResult

//                    val forecastsResult = WeatherRepository.getForecasts(location = location)
//                    forecasts.value = forecastsResult
                    loading = false
                }
            }
        }
    }
}

class WeatherViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WeatherViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return WeatherViewModel(application = application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}