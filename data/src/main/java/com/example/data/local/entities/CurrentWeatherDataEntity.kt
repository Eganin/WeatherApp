package com.example.data.local.entities

import androidx.room.Entity
import com.example.domain.models.weather.WeatherState


@Entity
data class CurrentWeatherDataEntity(
    val time: String,
    val temperatureCelsius: Double,
    val pressure: Double,
    val windSpeed: Double,
    val humidity: Double,
    val weatherCode : Int,
    val state: WeatherState
)