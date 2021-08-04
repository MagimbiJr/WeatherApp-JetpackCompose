package com.tana.weatherapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import com.tana.weatherapp.data.CurrentWeatherData

class WeatherViewModel : ViewModel() {
    private val _weatherData = MutableLiveData<CurrentWeatherData>()

    val weatherData = _weatherData.switchMap {
        liveData { emit(WeatherRepository.getCurrentWeather()) }
    }
}