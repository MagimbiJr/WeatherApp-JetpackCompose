package com.tana.weatherapp.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SignalCellularConnectedNoInternet4Bar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tana.weatherapp.viewmodel.WeatherViewModel

@Composable
fun ScreenLoading(viewModel:WeatherViewModel,modifier: Modifier) {
    if (viewModel.isBadResult) {
        Column {
            Icon(
                imageVector = Icons.Filled.SignalCellularConnectedNoInternet4Bar,
                contentDescription = null
            )
            Text(text = "No internet!. Please check your internet and try again")
        }
    }
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator(
            modifier = modifier.size(100.dp),
            color = MaterialTheme.colors.secondary
        )
    }
}