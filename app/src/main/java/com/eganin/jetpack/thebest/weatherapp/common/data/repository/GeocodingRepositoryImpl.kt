package com.eganin.jetpack.thebest.weatherapp.common.data.repository

import com.eganin.jetpack.thebest.weatherapp.common.data.local.WeatherDatabase
import com.eganin.jetpack.thebest.weatherapp.common.data.mapper.toGeocodingDto
import com.eganin.jetpack.thebest.weatherapp.common.data.mapper.toGeocodingEntity
import com.eganin.jetpack.thebest.weatherapp.common.domain.util.Resource
import com.eganin.jetpack.thebest.weatherapp.detailpage.data.remote.GeocodingApi
import com.eganin.jetpack.thebest.weatherapp.detailpage.data.remote.GeocodingDto
import com.eganin.jetpack.thebest.weatherapp.detailpage.domain.repository.GeocodingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GeocodingRepositoryImpl @Inject constructor(
    private val geocodingApi: GeocodingApi,
    db: WeatherDatabase
) : GeocodingRepository {

    private val geocodingDao = db.geocodingDao
    override suspend fun getGeoFromCity(
        cityName: String,
        fetchFromRemote: Boolean
    ): Flow<Resource<GeocodingDto>> {
        return flow {
            bodyForDataLoading {
                if (fetchFromRemote) {
                    val remoteInfo =
                        geocodingApi.getCoordFromCity(cityName = cityName).get(index = 0)
                    geocodingDao.insertGeocodingInfo(
                        geocodingInfo = remoteInfo.toGeocodingEntity(
                            cityName = cityName
                        )
                    )
                }
                geocodingDao.getGeocodingInfo(cityName = cityName).toGeocodingDto()
            }
        }
    }
}