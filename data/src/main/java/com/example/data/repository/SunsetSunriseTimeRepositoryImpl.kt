package com.example.data.repository

import com.example.data.local.WeatherDatabase
import com.example.data.mapper.toSunsetAndSunriseTime
import com.example.data.mapper.toSunsetSunriseTimeData
import com.example.data.mapper.toSunsetSunriseTimeDataEntity
import com.example.data.remote.SunsetSunriseTimeApi
import com.example.domain.repository.SunsetSunriseTimeRepository
import com.example.domain.models.sunsetsunrisetime.SunsetSunriseTimeData
import com.example.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class SunsetSunriseTimeRepositoryImpl @Inject constructor(
    private val api: SunsetSunriseTimeApi,
    db: WeatherDatabase
) : SunsetSunriseTimeRepository {

    private val sunsetSunriseDao = db.sunsetSunriseDao
    override fun getSunsetSunriseTime(
        lat: Double, lon: Double, fetchFromRemote: Boolean
    ): Flow<Resource<SunsetSunriseTimeData>> {

        return flow {
            bodyForDataLoading {
                if (fetchFromRemote) {
                    val remoteSunsetSunriseData = api.getTimesData(
                        lat = lat,
                        long = lon
                    ).results.toSunsetAndSunriseTime()

                    sunsetSunriseDao.clearSunsetAndSunriseInfo()
                    sunsetSunriseDao.insertSunsetAndSunriseInfo(
                        sunsetSunriseEntity = remoteSunsetSunriseData.toSunsetSunriseTimeDataEntity()
                    )
                }
                sunsetSunriseDao.getSunsetAndSunriseInfo().toSunsetSunriseTimeData()
            }
        }
    }
}