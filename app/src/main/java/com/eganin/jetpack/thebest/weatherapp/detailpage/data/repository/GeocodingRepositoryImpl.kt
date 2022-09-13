package com.eganin.jetpack.thebest.weatherapp.detailpage.data.repository

import com.eganin.jetpack.thebest.weatherapp.common.domain.repository.getDataForRepository
import com.eganin.jetpack.thebest.weatherapp.common.domain.util.Resource
import com.eganin.jetpack.thebest.weatherapp.detailpage.data.remote.GeocodingApi
import com.eganin.jetpack.thebest.weatherapp.detailpage.data.remote.GeocodingDto
import com.eganin.jetpack.thebest.weatherapp.detailpage.domain.repository.GeocodingRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GeocodingRepositoryImpl @Inject constructor(
    private val geocodingApi: GeocodingApi
) : GeocodingRepository {
    override suspend fun getGeoFromCity(cityName: String): Resource<GeocodingDto> {
        return getDataForRepository(
            data = geocodingApi.getCoordFromCity(cityName = cityName).get(index = 0)
        )
    }
}