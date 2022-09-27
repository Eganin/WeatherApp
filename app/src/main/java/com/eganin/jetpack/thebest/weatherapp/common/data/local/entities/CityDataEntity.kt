package com.eganin.jetpack.thebest.weatherapp.common.data.local.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index(value = ["cityName"], unique = true)])
data class CityDataEntity(
    @PrimaryKey
    val id: Int? = null,
    @Embedded
    val currentWeatherDataEntity: CurrentWeatherDataEntity?,
    val sunsetHour: Int,
    val sunriseHour: Int,
    val cityName: String
)
