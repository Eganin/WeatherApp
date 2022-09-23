package com.eganin.jetpack.thebest.weatherapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class GeocodingEntity(
    val latitude: Double,
    val longitude: Double,
    @PrimaryKey
    val id: Int? = null
)
