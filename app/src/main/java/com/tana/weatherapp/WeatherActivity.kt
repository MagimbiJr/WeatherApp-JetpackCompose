package com.tana.weatherapp

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.tana.weatherapp.ui.theme.CustomBackgroundColor
import com.tana.weatherapp.ui.theme.CustomSurfaceBackground
import com.tana.weatherapp.ui.theme.WeatherAppTheme
import com.tana.weatherapp.ui.theme.WeatherCustomTheme
import com.tana.weatherapp.viewmodel.LocationRepository
import com.tana.weatherapp.viewmodel.WeatherViewModel
import com.tana.weatherapp.viewmodel.WeatherViewModelFactory

class MainActivity : ComponentActivity() {
    private val viewModel: WeatherViewModel by viewModels {
        WeatherViewModelFactory(this.application)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContent {
            val weather = viewModel.weatherData.value
            //val dayForecast = viewModel.dayForecast.value
            //val forecasts = viewModel.forecasts.value
            val navHostController = rememberNavController()
            val systemUiController = rememberSystemUiController()
            systemUiController.setSystemBarsColor(CustomBackgroundColor)
            systemUiController.setStatusBarColor(CustomSurfaceBackground)
            WeatherCustomTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    WeatherNavGraph(
                        navHostController = navHostController,
                        currentWeatherData = weather,
                        //dayForecast = dayForecast,
                        forecasts = weather,
                        viewModel = viewModel
                    )
                }
            }
        }
        //viewModel.locationRepository.updateGPS()
        updateGPS()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            LOCATION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() &&grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    updateGPS()
                }
            }
            else -> Toast.makeText(
                this,
                "This app require permission to be granted",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun updateGPS() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            viewModel.locationRepository.updateGPS()
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_DENIED
            ) {
                Toast.makeText(this, "Access denied", Toast.LENGTH_SHORT).show()
                finish()
            }
        }  else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    LOCATION_REQUEST_CODE
                )
            }
        }
    }

    private fun startLocationUpdate() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            viewModel.locationRepository.startLocationUpdates()
        }  else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    LOCATION_REQUEST_CODE
                )
            }
        }
    }
    private fun stopLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            viewModel.locationRepository.stopLocationUpdates()
        }  else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    LOCATION_REQUEST_CODE
                )
            }
        }
    }

    override fun onResume() {
        super.onResume()
        startLocationUpdate()
    }

    override fun onPause() {
        super.onPause()
        stopLocationUpdates()
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    WeatherAppTheme {
        //WeatherImage("Android")
    }
}