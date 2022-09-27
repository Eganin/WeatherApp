package com.eganin.jetpack.thebest.weatherapp.common.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class GeocodingEntity(
    val latitude: Double,
    val longitude: Double,
    val cityName : String,
    @PrimaryKey
    val id: Int? = null
)
