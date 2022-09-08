package com.eganin.jetpack.thebest.weatherapp.search.domain.repository

import com.eganin.jetpack.thebest.weatherapp.search.data.remote.GeocodingDto

interface GeocodingRepository {
    suspend fun getGeoFromCity(cityName : String) : GeocodingDto
}