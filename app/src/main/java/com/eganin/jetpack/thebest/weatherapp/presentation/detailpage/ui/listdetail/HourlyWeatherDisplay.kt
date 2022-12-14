package com.eganin.jetpack.thebest.weatherapp.presentation.detailpage.ui.listdetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.domain.models.weather.WeatherData
import com.eganin.jetpack.thebest.weatherapp.presentation.ui.theme.AppTheme
import com.eganin.jetpack.thebest.weatherapp.presentation.ui.theme.Typography
import java.time.format.DateTimeFormatter

@Composable
fun HourlyWeatherDisplay(
    weatherData: WeatherData,
    modifier: Modifier = Modifier,
) {

    val formattedTime = remember(weatherData) {
        weatherData.time.format(
            DateTimeFormatter.ofPattern("HH")
        )
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        Text(
            text = if(formattedTime.toInt() in 0..12) "$formattedTime AM" else "$formattedTime PM",
            color = AppTheme.colors.secondaryText,
            style = Typography.h6
        )

        Image(
            painter = painterResource(id = weatherData.weatherType.iconRes),
            contentDescription = null,
            modifier = Modifier.width(40.dp)
        )
        Text(
            text = "${weatherData.temperatureCelsius}C",
            color = AppTheme.colors.secondaryText,
            style = Typography.h6
        )
    }
}