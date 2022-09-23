package com.eganin.jetpack.thebest.weatherapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.eganin.jetpack.thebest.weatherapp.common.domain.weather.WeatherState
import com.eganin.jetpack.thebest.weatherapp.common.domain.weather.WeatherType
import java.time.LocalDateTime

@Entity
data class WeatherDataEntity(
    val time: LocalDateTime,
    val temperatureCelsius: Double,
    val pressure: Double,
    val windSpeed: Double,
    val humidity: Double,
    val weatherType: WeatherType,
    val state: WeatherState,
    @PrimaryKey
    val id : Int? = null
)
