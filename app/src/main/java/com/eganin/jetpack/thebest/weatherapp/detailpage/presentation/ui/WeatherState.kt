package com.eganin.jetpack.thebest.weatherapp.detailpage.presentation.ui

import com.eganin.jetpack.thebest.weatherapp.common.domain.weather.WeatherInfo
import com.eganin.jetpack.thebest.weatherapp.detailpage.domain.sunsetsunrisetime.SunsetSunriseTimeData

data class WeatherState(
    val weatherInfo: WeatherInfo? = null,
    val sunsetAndSunriseTime : SunsetSunriseTimeData?= null,
    val dataStock: List<Int>? = null,
    val isLoading: Boolean = false,
    val searchQuery: String = "",
    val isRefreshing: Boolean = false,
    val error: String? = null
)
