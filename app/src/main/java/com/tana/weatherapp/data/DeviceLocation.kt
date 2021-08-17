package com.tana.weatherapp.data

import android.content.Context
import android.location.Geocoder

class DeviceLocation {

    fun getDeviceLocation(latitude: Double, longitude: Double, geocoder: Geocoder): String {
        val location = geocoder.getFromLocation(latitude, longitude, 1)
        return location[0].getAddressLine(0)
    }
}