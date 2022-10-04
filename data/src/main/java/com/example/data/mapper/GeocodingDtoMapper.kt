package com.example.data.mapper

import com.example.data.remote.GeocodingDto
import com.example.domain.models.geocoding.GeocodingData

fun GeocodingDto.toGeocodingData(): GeocodingData{
    return GeocodingData(
        latitude=latitude,
        longitude=longitude
    )
}