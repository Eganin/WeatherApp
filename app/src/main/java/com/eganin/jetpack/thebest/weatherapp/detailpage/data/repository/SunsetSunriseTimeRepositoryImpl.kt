package com.eganin.jetpack.thebest.weatherapp.detailpage.data.repository

import com.eganin.jetpack.thebest.weatherapp.common.domain.repository.getDataForRepository
import com.eganin.jetpack.thebest.weatherapp.common.domain.util.Resource
import com.eganin.jetpack.thebest.weatherapp.common.data.local.WeatherDatabase
import com.eganin.jetpack.thebest.weatherapp.common.data.mapper.toSunsetSunriseTimeData
import com.eganin.jetpack.thebest.weatherapp.common.data.mapper.toSunsetSunriseTimeDataEntity
import com.eganin.jetpack.thebest.weatherapp.detailpage.data.mapper.toSunsetAndSunriseTime
import com.eganin.jetpack.thebest.weatherapp.detailpage.data.remote.SunsetSunriseTimeApi
import com.eganin.jetpack.thebest.weatherapp.detailpage.domain.repository.SunsetSunriseTimeRepository
import com.eganin.jetpack.thebest.weatherapp.detailpage.domain.sunsetsunrisetime.SunsetSunriseTimeData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class SunsetSunriseTimeRepositoryImpl @Inject constructor(
    private val api: SunsetSunriseTimeApi,
    db: WeatherDatabase
) : SunsetSunriseTimeRepository {

    private val sunsetSunriseDao = db.sunsetSunriseDao
    override suspend fun getSunsetSunriseTime(
        lat: Double, lon: Double, fetchFromRemote: Boolean
    ): Resource<SunsetSunriseTimeData> {
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

        return getDataForRepository(
            data = sunsetSunriseDao.getSunsetAndSunriseInfo().toSunsetSunriseTimeData()
        )
    }
}