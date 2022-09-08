package com.eganin.jetpack.thebest.weatherapp.weeklist.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.eganin.jetpack.thebest.weatherapp.R
import com.eganin.jetpack.thebest.weatherapp.common.domain.weather.WeatherData
import com.eganin.jetpack.thebest.weatherapp.ui.theme.AppTheme
import com.eganin.jetpack.thebest.weatherapp.ui.theme.Typography
import java.time.format.DateTimeFormatter

@Composable
fun DailyWeatherDisplay(
    weatherData: WeatherData,
    indexDay: Int,
    modifier: Modifier = Modifier,
) {

    Row(
        modifier = modifier.background(AppTheme.colors.primaryBackground),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = getTitleFromIndexDay(indexDay = indexDay),
                style = Typography.h5,
                color = AppTheme.colors.secondaryText
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = "${weatherData.time.dayOfMonth}  ${
                    weatherData.time.month.toString().slice(0..2)
                }",
                style = Typography.h6,
                color = AppTheme.colors.primaryText
            )
        }

        Text(
            text = "${weatherData.temperatureCelsius} C",
            style = Typography.h4,
            color = AppTheme.colors.secondaryText
        )
        Image(
            painter = painterResource(id = weatherData.weatherType.iconRes),
            contentDescription = null,
            modifier = Modifier.width(40.dp)
        )
    }
}

@Composable
private fun getTitleFromIndexDay(indexDay: Int): String {
    return when (indexDay) {
        0 -> stringResource(id = R.string.monday_label)
        1 -> stringResource(R.string.tuesday_label)
        2 -> stringResource(R.string.wednesday_label)
        3 -> stringResource(R.string.thursday_label)
        4 -> stringResource(R.string.friday_label)
        5 -> stringResource(R.string.saturday_label)
        else -> stringResource(R.string.sunday_label)
    }
}