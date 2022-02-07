package com.tana.weatherapp.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.tana.weatherapp.components.ScreenLoading
import com.tana.weatherapp.data.WeatherData
import com.tana.weatherapp.ui.theme.SecondaryTextColor
import com.tana.weatherapp.viewmodel.WeatherViewModel
import java.time.Instant
import java.time.ZoneId

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ForecastsScreen(
    //currentDayForecast: CurrentDayForecast,
    forecast: WeatherData,
    viewModel: WeatherViewModel,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(horizontal = 32.dp, vertical = 16.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (viewModel.loading) {
            ScreenLoading(viewModel = viewModel, modifier = modifier)
        } else {
            Text(
                text = "Forecasts Report",
                style = MaterialTheme.typography.h4,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = modifier.padding(15.dp))
            TodayForecast(
                currentDayForecast = forecast,
                modifier = modifier
            )
            Spacer(modifier = modifier.padding(15.dp))
            Row(
                modifier = modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Next Forecast",
                    style = MaterialTheme.typography.h5,
                    color = SecondaryTextColor
                )
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        imageVector = Icons.Default.CalendarToday,
                        contentDescription = null,
                        tint = SecondaryTextColor
                    )
                }
            }
            Spacer(modifier = modifier.padding(10.dp))
            Forecasts(
                forecast = forecast,
                modifier = modifier
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Forecasts(
    forecast: WeatherData,
    modifier: Modifier
) {
    val daysLists = forecast.daily
    LazyColumn(
        modifier = modifier.padding(bottom = 40.dp)
    ) {
        if (daysLists == null) {
            item { }
        } else {
            items(daysLists) { day ->
                Card(
                    modifier = modifier.padding(top = 8.dp, bottom = 8.dp),
                    shape = RoundedCornerShape(15.dp)
                ) {
                    Row(
                        modifier = modifier
                            .padding(vertical = 18.dp, horizontal = 16.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column() {
                            day.epochDate?.let { epochDate ->
                                val day =
                                    Instant.ofEpochSecond(epochDate).atZone(ZoneId.systemDefault())
                                        .dayOfWeek.plus(1)
                                Text(
                                    text = day.toString(),
                                    style = MaterialTheme.typography.body1
                                )
                            }
                            Spacer(modifier = modifier.padding(3.dp))
                            day.epochDate?.let { epochDate ->
                                val month =
                                    Instant.ofEpochSecond(epochDate).atZone(ZoneId.systemDefault())
                                        .month.name.slice(0..2)
                                val day = Instant.ofEpochSecond(epochDate)
                                    .atZone(ZoneId.systemDefault()).dayOfMonth.plus(1)
                                val date = "$month, $day"
                                Text(
                                    text = date,
                                    style = MaterialTheme.typography.body1,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.W500,
                                    color = SecondaryTextColor
                                )
                            }
                        }
                        Row() {
                            day.temperature?.day?.let { maxTemp ->
                                Text(
                                    text = maxTemp.toString(),
                                    style = MaterialTheme.typography.h5,
                                    fontSize = 25.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Text(text = "℃")
                        }
                        day.weather[0].icon?.let { icon ->
//                                val painter = rememberImagePainter(data = "https:$icon")
                            val painter =
                                rememberImagePainter(data = "https://openweathermap.org/img/wn/$icon@2x.png")
                            Image(
                                painter = painter,
                                contentDescription = null,
                                modifier = modifier.size(50.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TodayForecast(
//    currentDayForecast: CurrentDayForecast,
    currentDayForecast: WeatherData,
    modifier: Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Today",
            style = MaterialTheme.typography.h5,
            fontWeight = FontWeight.Bold,
            color = SecondaryTextColor
        )
//        currentDayForecast.forecast?.forecastDay?.get(0)?.date?.let { epochDate ->
        currentDayForecast.daily[0]?.epochDate?.let { epochDate ->
            val month = Instant.ofEpochSecond(epochDate).atZone(ZoneId.systemDefault())
                .month.name.slice(0..2)
            val day = Instant.ofEpochSecond(epochDate).atZone(ZoneId.systemDefault()).dayOfMonth
            val year = Instant.ofEpochSecond(epochDate).atZone(ZoneId.systemDefault()).year
            val date = "$month $day, $year"
            Text(
                text = date,
                color = SecondaryTextColor
            )
        }
    }
    Spacer(modifier = modifier.padding(10.dp))
    DayForecastList(
        forecastDay = currentDayForecast,
        modifier = modifier
    )
}