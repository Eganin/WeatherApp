package com.eganin.jetpack.thebest.weatherapp.common.data.repository

import com.eganin.jetpack.thebest.weatherapp.common.data.local.WeatherDatabase
import com.eganin.jetpack.thebest.weatherapp.common.data.mapper.toSunsetSunriseTimeData
import com.eganin.jetpack.thebest.weatherapp.common.data.mapper.toSunsetSunriseTimeDataEntity
import com.eganin.jetpack.thebest.weatherapp.common.domain.util.Resource
import com.eganin.jetpack.thebest.weatherapp.detailpage.data.mapper.toSunsetAndSunriseTime
import com.eganin.jetpack.thebest.weatherapp.detailpage.data.remote.SunsetSunriseTimeApi
import com.eganin.jetpack.thebest.weatherapp.detailpage.domain.repository.SunsetSunriseTimeRepository
import com.eganin.jetpack.thebest.weatherapp.detailpage.domain.sunsetsunrisetime.SunsetSunriseTimeData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class SunsetSunriseTimeRepositoryImpl @Inject constructor(
    private val api: SunsetSunriseTimeApi,
    db: WeatherDatabase
) : SunsetSunriseTimeRepository {

    private val sunsetSunriseDao = db.sunsetSunriseDao
    override suspend fun getSunsetSunriseTime(
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