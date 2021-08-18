package com.tana.weatherapp.viewmodel

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Build
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.lifecycle.LiveData
import com.google.android.gms.location.*
import com.tana.weatherapp.DEFAULT_UPDATE_INTERVAL
import com.tana.weatherapp.FAST_UPDATE_INTERVAL
import com.tana.weatherapp.LOCATION_REQUEST_CODE
import com.tana.weatherapp.data.DeviceLocation

class LocationRepository(private val context: Context) : LiveData<String>() {
     val deviceLocation = DeviceLocation()
    val fusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
    private val geocoder = Geocoder(context)

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            super.onLocationResult(locationResult)
            value = deviceLocation.getDeviceLocation(
                locationResult.lastLocation.latitude,
                locationResult.lastLocation.longitude,
                geocoder
            )
        }
    }


    companion object {
        private val locationRequest = LocationRequest.create().apply {
            interval = DEFAULT_UPDATE_INTERVAL
            fastestInterval = FAST_UPDATE_INTERVAL
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
    }

    fun startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null)
        }
    }

    fun stopLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(context,
            Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.removeLocationUpdates(locationCallback)
        }
    }

    fun updateGPS() {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location ->
                value = deviceLocation.getDeviceLocation(
                    location.latitude,
                    location.longitude,
                    geocoder
                )
            }
        }
    }
}