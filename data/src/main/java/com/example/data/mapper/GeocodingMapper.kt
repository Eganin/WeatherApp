package com.example.data.mapper

import com.example.data.local.entities.GeocodingEntity
import com.example.data.remote.GeocodingDto

fun GeocodingDto.toGeocodingEntity(cityName : String): GeocodingEntity {
    return GeocodingEntity(
        latitude = latitude,
        longitude = longitude,
        cityName = cityName
    )
}

fun GeocodingEntity.toGeocodingDto(): GeocodingDto {
    return GeocodingDto(
        latitude = latitude,
        longitude = longitude
    )
}