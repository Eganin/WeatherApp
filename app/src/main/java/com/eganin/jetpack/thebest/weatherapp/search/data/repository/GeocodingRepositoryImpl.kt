package com.eganin.jetpack.thebest.weatherapp.search.data.repository

import com.eganin.jetpack.thebest.weatherapp.search.data.remote.GeocodingApi
import com.eganin.jetpack.thebest.weatherapp.search.data.remote.GeocodingDto
import com.eganin.jetpack.thebest.weatherapp.search.domain.repository.GeocodingRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GeocodingRepositoryImpl  @Inject constructor(
    private val geocodingApi: GeocodingApi
): GeocodingRepository {
    override suspend fun getGeoFromCity(cityName : String): GeocodingDto {
        return withContext(Dispatchers.IO){
            geocodingApi.getCoordFromCity(cityName = cityName)
        }
    }
}