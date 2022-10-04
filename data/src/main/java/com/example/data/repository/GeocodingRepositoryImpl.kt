package com.example.data.repository

import com.example.data.local.WeatherDatabase
import com.example.data.mapper.toGeocodingData
import com.example.data.mapper.toGeocodingDto
import com.example.data.mapper.toGeocodingEntity
import com.example.data.remote.GeocodingApi
import com.example.data.remote.GeocodingDto
import com.example.domain.models.geocoding.GeocodingData
import com.example.domain.repository.GeocodingRepository
import com.example.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GeocodingRepositoryImpl @Inject constructor(
    private val geocodingApi: GeocodingApi,
    db: WeatherDatabase
) : GeocodingRepository {

    private val geocodingDao = db.geocodingDao
    override fun getGeoFromCity(
        cityName: String,
        fetchFromRemote: Boolean
    ): Flow<Resource<GeocodingData>> {
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
                    .toGeocodingData()
            }
        }
    }
}