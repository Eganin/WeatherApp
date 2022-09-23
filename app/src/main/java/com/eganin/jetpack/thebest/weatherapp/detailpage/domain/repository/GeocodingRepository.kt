package com.eganin.jetpack.thebest.weatherapp.detailpage.domain.repository

import com.eganin.jetpack.thebest.weatherapp.common.domain.util.Resource
import com.eganin.jetpack.thebest.weatherapp.detailpage.data.remote.GeocodingDto

interface GeocodingRepository {
    suspend fun getGeoFromCity(cityName : String,fetchFromRemote: Boolean) : Resource<GeocodingDto>
}