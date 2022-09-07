package com.eganin.jetpack.thebest.weatherapp.detailpage.presentation.ui

import com.eganin.jetpack.thebest.weatherapp.detailpage.domain.weather.WeatherInfo

data class WeatherState(
    val weatherInfo: WeatherInfo? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
