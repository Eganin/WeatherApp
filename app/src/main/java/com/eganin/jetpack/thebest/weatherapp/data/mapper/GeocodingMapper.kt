package com.eganin.jetpack.thebest.weatherapp.data.mapper

import com.eganin.jetpack.thebest.weatherapp.data.local.entities.GeocodingEntity
import com.eganin.jetpack.thebest.weatherapp.detailpage.data.remote.GeocodingDto

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