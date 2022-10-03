package com.eganin.jetpack.thebest.weatherapp.presentation.detailpage

import com.example.domain.models.weather.WeatherInfo
import com.example.domain.models.sunsetsunrisetime.SunsetSunriseTimeData

data class WeatherState(
    val weatherInfo: WeatherInfo? = null,
    val sunsetAndSunriseTime : SunsetSunriseTimeData?= null,
    val dataStock: List<Int>? = null,
    val isLoading: Boolean = false,
    val searchQuery: String = "",
    val isRefreshing: Boolean = false,
    val error: String? = null
)
