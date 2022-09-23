package com.eganin.jetpack.thebest.weatherapp.detailpage.data.repository

import com.eganin.jetpack.thebest.weatherapp.common.domain.repository.getDataForRepository
import com.eganin.jetpack.thebest.weatherapp.common.domain.util.Resource
import com.eganin.jetpack.thebest.weatherapp.data.local.WeatherDatabase
import com.eganin.jetpack.thebest.weatherapp.data.mapper.toGeocodingDto
import com.eganin.jetpack.thebest.weatherapp.data.mapper.toGeocodingEntity
import com.eganin.jetpack.thebest.weatherapp.detailpage.data.remote.GeocodingApi
import com.eganin.jetpack.thebest.weatherapp.detailpage.data.remote.GeocodingDto
import com.eganin.jetpack.thebest.weatherapp.detailpage.domain.repository.GeocodingRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GeocodingRepositoryImpl @Inject constructor(
    private val geocodingApi: GeocodingApi,
    db: WeatherDatabase
) : GeocodingRepository {

    val geocodingDao = db.geocodingDao
    override suspend fun getGeoFromCity(
        cityName: String,
        fetchFromRemote: Boolean
    ): Resource<GeocodingDto> {

        if (fetchFromRemote) {
            val remoteInfo = geocodingApi.getCoordFromCity(cityName = cityName).get(index = 0)
            //geocodingDao.clearGeocodingInfo()
            geocodingDao.insertGeocodingInfo(geocodingInfo = remoteInfo.toGeocodingEntity(cityName = cityName))
        }

        return getDataForRepository(
            data = geocodingDao.getGeocodingInfo(cityName = cityName).toGeocodingDto()
        )
    }
}