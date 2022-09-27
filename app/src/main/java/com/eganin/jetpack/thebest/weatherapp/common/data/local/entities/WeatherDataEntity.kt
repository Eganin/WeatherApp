package com.eganin.jetpack.thebest.weatherapp.common.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.eganin.jetpack.thebest.weatherapp.common.domain.weather.WeatherState
import com.squareup.moshi.Json
import java.time.LocalDateTime

@Entity
data class WeatherDataEntity(
    val time: List<String>,
    val temperatures: List<Double>,
    val weatherCodes: List<Int>,
    val pressures: List<Double>,
    val winsSpeeds: List<Double>,
    val humidities: List<Double>,
    @PrimaryKey
    val id : Int? = null
)
