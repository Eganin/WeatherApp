package com.eganin.jetpack.thebest.weatherapp.presentation.detailpage.ui.listdetail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.eganin.jetpack.thebest.weatherapp.R
import com.eganin.jetpack.thebest.weatherapp.presentation.detailpage.WeatherState
import com.eganin.jetpack.thebest.weatherapp.presentation.ui.theme.AppTheme
import com.eganin.jetpack.thebest.weatherapp.presentation.ui.theme.Typography

@Composable
fun WeatherForecast(
    state: WeatherState,
    modifier: Modifier = Modifier
) {
    state.weatherInfo?.weatherDataPerDay?.get(0)?.let { data ->
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Text(
                text = stringResource(R.string.today_caption),
                style = Typography.h5,
                color = AppTheme.colors.secondaryText
            )
            Spacer(modifier = Modifier.height(16.dp))
            LazyRow {
                items(data) { weatherData ->
                    HourlyWeatherDisplay(
                        weatherData = weatherData,
                        modifier = Modifier
                            .height(100.dp)
                            .padding(horizontal = 16.dp)
                    )
                }
            }
        }
    }
}