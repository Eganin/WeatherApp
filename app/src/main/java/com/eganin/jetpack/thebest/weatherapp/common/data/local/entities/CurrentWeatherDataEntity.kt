package com.eganin.jetpack.thebest.weatherapp.common.data.local.entities

import com.eganin.jetpack.thebest.weatherapp.common.domain.weather.WeatherState
import java.time.LocalDateTime


data class CurrentWeatherDataEntity(
    val time: String,
    val temperatureCelsius: Double,
    val pressure: Double,
    val windSpeed: Double,
    val humidity: Double,
    val weatherCode : Int,
    val state: WeatherState
)