package com.eganin.jetpack.thebest.weatherapp.detailpage.domain.repository

import com.eganin.jetpack.thebest.weatherapp.common.domain.util.Resource
import com.eganin.jetpack.thebest.weatherapp.detailpage.data.remote.GeocodingDto
import kotlinx.coroutines.flow.Flow

interface GeocodingRepository {
    suspend fun getGeoFromCity(cityName : String,fetchFromRemote: Boolean) : Flow<Resource<GeocodingDto>>
}