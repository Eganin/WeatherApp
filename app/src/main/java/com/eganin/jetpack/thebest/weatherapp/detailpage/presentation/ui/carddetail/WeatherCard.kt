package com.eganin.jetpack.thebest.weatherapp.detailpage.presentation.ui.carddetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.eganin.jetpack.thebest.weatherapp.R
import com.eganin.jetpack.thebest.weatherapp.detailpage.presentation.ui.WeatherState
import com.eganin.jetpack.thebest.weatherapp.detailpage.presentation.ui.chart.ChartWeather
import com.eganin.jetpack.thebest.weatherapp.ui.theme.AppTheme
import com.eganin.jetpack.thebest.weatherapp.ui.theme.Typography
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt

@Composable
fun WeatherCard(
    state: WeatherState,
    backgroundColor: Color,
    modifier: Modifier = Modifier
) {
    state.weatherInfo?.currentWeatherData?.let { data ->
        Card(
            backgroundColor = backgroundColor,
            shape = RoundedCornerShape(10.dp),
            modifier = modifier.padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = state.searchQuery,
                        style = Typography.caption,
                        color = AppTheme.colors.primaryText
                    )
                    Text(
                        text = "${stringResource(R.string.today_caption)} ${
                            data.time.format(
                                DateTimeFormatter.ofPattern("HH:mm")
                            )
                        }",
                        style = Typography.caption,
                        color = AppTheme.colors.primaryText
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Image(
                    painter = painterResource(id = data.weatherType.iconRes),
                    contentDescription = data.weatherType.weatherDesc,
                    modifier = Modifier.width(200.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "${data.temperatureCelsius}C",
                    style = Typography.h3,
                    color = AppTheme.colors.primaryText
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = data.weatherType.weatherDesc,
                    style = Typography.h5,
                    color = AppTheme.colors.primaryText
                )
                Spacer(modifier = Modifier.height(32.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    WeatherDataDisplay(
                        value = data.pressure.roundToInt(),
                        unit = stringResource(R.string.hpa_label_weather_data_display),
                        icon = ImageVector.vectorResource(id = R.drawable.ic_pressure),
                        textStyle = Typography.caption
                    )
                    WeatherDataDisplay(
                        value = data.humidity.roundToInt(),
                        unit = stringResource(R.string.percent_label_weather_data_display),
                        icon = ImageVector.vectorResource(id = R.drawable.ic_drop),
                        textStyle = Typography.caption
                    )
                    WeatherDataDisplay(
                        value = data.windSpeed.roundToInt(),
                        unit = stringResource(R.string.speed_label_weather_data_display),
                        icon = ImageVector.vectorResource(id = R.drawable.ic_wind),
                        textStyle = Typography.caption
                    )
                }
                state.dataStock?.let {
                    ChartWeather(info = it)
                }
            }
        }
    }
}