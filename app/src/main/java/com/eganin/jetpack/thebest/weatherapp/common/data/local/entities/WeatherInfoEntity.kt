package com.eganin.jetpack.thebest.weatherapp.common.data.local.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.eganin.jetpack.thebest.weatherapp.common.domain.weather.WeatherData

@Entity
data class WeatherInfoEntity(
    val weatherDataPerDay : Map<Int , List<CurrentWeatherDataEntity>>,
    @Embedded
    val currentWeatherData : CurrentWeatherDataEntity?,
    @PrimaryKey
    val id : Int? = null
)
