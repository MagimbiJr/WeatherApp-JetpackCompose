package com.tana.weatherapp.screens

import android.location.Geocoder
import android.os.Build
import android.provider.Settings
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SignalCellularConnectedNoInternet4Bar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.tana.weatherapp.WEATHER_SCREEN_TAG
import com.tana.weatherapp.components.NavigationItem
import com.tana.weatherapp.components.ScreenLoading
//import com.tana.weatherapp.data.CurrentWeatherData
//import com.tana.weatherapp.data.CurrentDayForecast
import com.tana.weatherapp.data.WeatherData
import com.tana.weatherapp.ui.theme.SecondaryTextColor
import com.tana.weatherapp.ui.theme.TextLinkColor
import com.tana.weatherapp.viewmodel.WeatherViewModel
import java.time.Instant
import java.time.ZoneId

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WeatherScreen(
    //currentWeather: CurrentWeatherData,
    currentWeather: WeatherData,
    //dayForecast: CurrentDayForecast,
    viewModel: WeatherViewModel,
    navHostController: NavHostController,
    modifier: Modifier = Modifier
) {
    var isCardSelected = viewModel.cardToggle.value
    val (forecastSelected, setForecastSelection) = remember { mutableStateOf(isCardSelected) }
    val (airWindSelected, setAirWindSelection) = remember { mutableStateOf(false) }
    val forecastClicked = {
        setAirWindSelection(false)
        setForecastSelection(true)
    }
    val airWindClicked = {
        setForecastSelection(false)
        setAirWindSelection(true)
    }

    Column(
        modifier = modifier
            .padding(horizontal = 32.dp, vertical = 16.dp)
            .padding(top = 10.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (viewModel.loading) {
            ScreenLoading(viewModel = viewModel, modifier = modifier)
        } else {
            LocationInfo(
                weatherData = currentWeather,
                viewModel = viewModel,
                modifier = modifier
            )
            Spacer(modifier = modifier.padding(15.dp))
            ToggleCards(
                isCardSelected = isCardSelected,
                forecastSelected = forecastSelected,
                airWindSelected = airWindSelected,
                onForecastClicked = forecastClicked,
                onAirCondClicked = airWindClicked,
                modifier = modifier
            )
            Spacer(modifier = modifier.padding(15.dp))
            WeatherImage(currentWeather = currentWeather, modifier = modifier)
            Spacer(modifier = modifier.padding(5.dp))
            WeatherInfo(currentWeather = currentWeather, modifier = modifier)
            Spacer(modifier = modifier.padding(15.dp))
            DayForecast(
                //dayForecast = dayForecast,
                dayForecast = currentWeather,
                navHostController = navHostController,
                modifier = modifier
            )
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DayForecast(
    //dayForecast: CurrentDayForecast,
    dayForecast: WeatherData,
    navHostController: NavHostController,
    modifier: Modifier
) {
    Column() {
        Row(
            modifier = modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Today",
                style = MaterialTheme.typography.h5,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "View full report",
                style = MaterialTheme.typography.body1,
                color = TextLinkColor,
                modifier = modifier.clickable { navHostController.navigate(NavigationItem.Forecasts.route) }
            )
        }
        Spacer(modifier = modifier.padding(12.dp))
        DayForecastList(forecastDay = dayForecast, modifier = modifier)
    }
}

@OptIn(ExperimentalCoilApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DayForecastList(
    //forecastDay: CurrentDayForecast,
    forecastDay: WeatherData,
    modifier: Modifier
) {
    //val hoursForecast = forecastDay.forecast?.forecastDay
    val hoursForecast = forecastDay.hourly
    LazyRow() {
        if (hoursForecast == null) {
            item { }
        } else {
            itemsIndexed(hoursForecast) { index, hour ->
                Card(
                    shape = RoundedCornerShape(15.dp),
                    modifier = modifier.padding(end = 15.dp),
                    backgroundColor = if (index == 0) MaterialTheme.colors.secondary else
                        MaterialTheme.colors.surface,
                    contentColor = MaterialTheme.colors.onSurface
                ) {
                    Row(
                        modifier = modifier.padding(horizontal = 18.dp, vertical = 10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        //hour.condition?.icon?.let { iconUrl ->
                        hour.weather[0].icon?.let { iconUrl ->

                            //val painter = rememberImagePainter(data = "https:$iconUrl")
                            val painter =
                                rememberImagePainter(data = "https://openweathermap.org/img/wn/$iconUrl@2x.png")
                            Image(
                                painter = painter,
                                contentDescription = null,
                                modifier = modifier.size(80.dp)
                            )
                        }
                        Spacer(modifier = modifier.padding(5.dp))
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            //hour.time?.let { timeEpoch ->
                            hour.time?.let { timeEpoch ->
                                val formatedTime = Instant.ofEpochSecond(timeEpoch)
                                    .atZone(ZoneId.systemDefault()).toLocalTime()
                                Text(
                                    text = formatedTime.toString(),
                                    style = MaterialTheme.typography.body1,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Spacer(modifier = modifier.padding(2.dp))
                            //hour.temp?.let { temp ->
                            hour.temperature?.let { temp ->
                                Row() {
                                    Text(
                                        text = temp.toString(),
                                        style = MaterialTheme.typography.body1,
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = "℃",
                                        fontSize = 12.sp
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun WeatherInfo(
    //currentWeather: CurrentWeatherData,
    currentWeather: WeatherData,
    modifier: Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Temp",
                color = SecondaryTextColor
            )
            Spacer(modifier = modifier.padding(2.dp))
            //currentWeather.current?.temperature.let { temp ->
            currentWeather.current?.temperature?.let { temp ->
                Row() {
                    Text(
                        text = "$temp",
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    )
                    Text(
                        text = "℃",
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    )
                }
            }
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Wind",
                color = SecondaryTextColor
            )
            Spacer(modifier = modifier.padding(2.dp))
            //currentWeather.current?.wind.let { wind ->
            currentWeather.current?.windSpeed.let { wind ->
                Text(
                    text = "$wind km/h",
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                )
            }
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Humidity",
                color = SecondaryTextColor
            )
            Spacer(modifier = modifier.padding(2.dp))
            currentWeather.current?.humidity.let { humidity ->
                Text(
                    text = "$humidity%",
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                )
            }
        }
    }
}

@Composable
fun WeatherImage(
    //currentWeather: CurrentWeatherData,
    currentWeather: WeatherData,
    modifier: Modifier
) {
    //currentWeather.current?.condition?.icon?.let { icon ->
    currentWeather.current?.weather?.get(0)?.icon?.let { icon ->
        val painter = "https://openweathermap.org/img/wn/$icon@2x.png"
        Image(
            //painter = rememberImagePainter(data = "https:$icon"),
            painter = rememberImagePainter(data = painter),
            contentDescription = null,
            modifier = modifier.size(190.dp),
            contentScale = ContentScale.FillBounds
        )
    }
}

@Composable
fun ToggleCards(
    isCardSelected: Boolean,
    forecastSelected: Boolean,
    airWindSelected: Boolean,
    onForecastClicked: () -> Unit,
    onAirCondClicked: () -> Unit,
    modifier: Modifier
) {
    val forecastBackground =
        if (forecastSelected) MaterialTheme.colors.secondary else MaterialTheme.colors.surface
    val airWindBackground =
        if (airWindSelected) MaterialTheme.colors.secondary else MaterialTheme.colors.surface
    val forecastCardShape =
        if (forecastSelected) RoundedCornerShape(12.dp) else RoundedCornerShape(0.dp)
    val airWindCardShape =
        if (forecastSelected) RoundedCornerShape(12.dp) else RoundedCornerShape(0.dp)
    val forecastTextColor =
        if (forecastSelected) MaterialTheme.colors.onBackground else SecondaryTextColor
    val airWindTextColor =
        if (airWindSelected) MaterialTheme.colors.onBackground else SecondaryTextColor
    Card(
        modifier = modifier.clip(RoundedCornerShape(12.dp)),
    ) {
        Row() {
            CustomCard(
                onClick = onForecastClicked,
                isCardSelected = if (isCardSelected) forecastSelected else airWindSelected,
                background = forecastBackground,
                contentColor = forecastTextColor,
                shapes = forecastCardShape
            ) {
                Text(
                    text = "Forecast",
                    modifier = modifier
                        .padding(horizontal = 12.dp, vertical = 10.dp)
                        .padding(start = 12.dp, end = 12.dp)
                )
            }
            CustomCard(
                onClick = onAirCondClicked,
                isCardSelected = if (isCardSelected) airWindSelected else forecastSelected,
                background = airWindBackground,
                contentColor = airWindTextColor,
                shapes = airWindCardShape
            ) {
                Text(
                    text = "Air Quality",
                    modifier = modifier
                        .padding(horizontal = 12.dp, vertical = 10.dp)
                        .padding(start = 12.dp, end = 12.dp)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CustomCard(
    onClick: () -> Unit,
    isCardSelected: Boolean,
    background: Color,
    contentColor: Color,
    modifier: Modifier = Modifier,
    shapes: Shape = RectangleShape,
    border: BorderStroke? = null,
    elevation: Dp = 1.dp,
    content: @Composable () -> Unit
) {
    Surface(
        onClick = onClick,
        modifier = modifier,
        shape = shapes,
        color = background,
        contentColor = contentColor,
        border = border,
        elevation = elevation,
        content = content
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun LocationInfo(
    //weatherData: CurrentWeatherData,
    weatherData: WeatherData,
    viewModel: WeatherViewModel,
    modifier: Modifier
) {
    //weatherData.location?.name?.let { name ->
    weatherData.let {
        val lat = viewModel.locationRepository.latitude
        val lon = viewModel.locationRepository.longitude
        val geocoder = Geocoder(LocalContext.current)
        val address = geocoder.getFromLocation(lat, lon, 1)
        val name = address[0].locality
        if (name != null) {
            Text(
                text = name,
                style = MaterialTheme.typography.h4,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }

    }
    Spacer(modifier = modifier.padding(6.dp))
    weatherData.current?.epochDate?.let { dateEpoch ->
        val month = Instant.ofEpochSecond(dateEpoch)
            .atZone(ZoneId.systemDefault()).toLocalDate().month.name.slice(0..2)
        val day = Instant.ofEpochSecond(dateEpoch)
            .atZone(ZoneId.systemDefault()).toLocalDate().dayOfMonth
        val year = Instant.ofEpochSecond(dateEpoch)
            .atZone(ZoneId.systemDefault()).year
        val date = "$month $day, $year"

        Text(
            text = date,
            style = MaterialTheme.typography.body1,
            fontSize = 17.sp,
            color = SecondaryTextColor
        )
    }
}