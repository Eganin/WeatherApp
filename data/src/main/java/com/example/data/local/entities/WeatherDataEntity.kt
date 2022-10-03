package com.example.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

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
