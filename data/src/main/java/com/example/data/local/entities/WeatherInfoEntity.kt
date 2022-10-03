package com.example.data.local.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class WeatherInfoEntity(
    val weatherDataPerDay : Map<Int , List<CurrentWeatherDataEntity>>,
    @Embedded
    val currentWeatherData : CurrentWeatherDataEntity?,
    @PrimaryKey
    val id : Int? = null
)
