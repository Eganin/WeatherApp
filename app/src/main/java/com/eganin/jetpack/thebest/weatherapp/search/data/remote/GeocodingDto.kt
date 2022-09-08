package com.eganin.jetpack.thebest.weatherapp.search.data.remote

import com.squareup.moshi.Json

data class GeocodingDto(
    @field:Json(name = "lat")
    val latitude: Double,
    @field:Json(name = "lon")
    val longitude: Double
)
