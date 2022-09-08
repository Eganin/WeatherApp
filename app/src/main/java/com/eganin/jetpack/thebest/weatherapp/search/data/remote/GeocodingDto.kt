package com.eganin.jetpack.thebest.weatherapp.search.data.remote

import com.squareup.moshi.Json
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GeocodingDto(
    @SerialName("lat")
    val latitude: Double,
    @SerialName("lon")
    val longitude: Double
)
