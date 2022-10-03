package com.example.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GeocodingDto(
    @SerialName("lat")
    val latitude: Double,
    @SerialName("lon")
    val longitude: Double
)
