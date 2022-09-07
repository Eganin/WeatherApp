package com.eganin.jetpack.thebest.weatherapp.detailpage.presentation.ui.weathercard

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.eganin.jetpack.thebest.weatherapp.detailpage.domain.weather.WeatherData
import com.eganin.jetpack.thebest.weatherapp.ui.theme.AppTheme
import com.eganin.jetpack.thebest.weatherapp.ui.theme.Typography

@Composable
fun StockChart(info : List<WeatherData>,modifier: Modifier = Modifier) {

    Card(
        backgroundColor = AppTheme.colors.secondaryBackground,
        shape = RoundedCornerShape(10.dp),
        modifier = modifier
            .padding(16.dp)
            .width(300.dp)
            .height(150.dp)
    ) {
        Column() {
            Text(
                text = "Temperature",
                style = Typography.h5,
                color = AppTheme.colors.primaryText,
                modifier = Modifier.align(Alignment.Start)
            )
        }

    }
}