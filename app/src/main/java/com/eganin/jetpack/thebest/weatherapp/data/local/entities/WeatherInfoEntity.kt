package com.eganin.jetpack.thebest.weatherapp.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.eganin.jetpack.thebest.weatherapp.common.domain.weather.WeatherData

@Entity
data class WeatherInfoEntity(
    val weatherDataPerDay : Map<Int , List<WeatherData>>,
    val currentWeatherData : WeatherData,
    @PrimaryKey
    val id : Int? = null
)
