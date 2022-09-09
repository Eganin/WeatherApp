package com.eganin.jetpack.thebest.weatherapp.detailpage.data.repository

import com.eganin.jetpack.thebest.weatherapp.common.domain.util.Resource
import com.eganin.jetpack.thebest.weatherapp.detailpage.data.mapper.toSunsetAndSunriseTime
import com.eganin.jetpack.thebest.weatherapp.detailpage.data.remote.SunsetSunriseTimeApi
import com.eganin.jetpack.thebest.weatherapp.detailpage.domain.repository.SunsetSunriseTimeRepository
import com.eganin.jetpack.thebest.weatherapp.detailpage.domain.sunsetsunrisetime.SunsetSunriseTimeData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class SunsetSunriseTimeRepositoryImpl @Inject constructor(
    private val api: SunsetSunriseTimeApi
) : SunsetSunriseTimeRepository {
    override suspend fun getSunsetSunriseTime(
        lat: Double, lon: Double
    ): Resource<SunsetSunriseTimeData> {
        return withContext(Dispatchers.IO) {
            try {
                Resource.Success(
                    data = api.getTimesData(lat = lat, long = lon).results.toSunsetAndSunriseTime()
                )
            } catch (e: Exception) {
                e.printStackTrace()
                Resource.Error(message = e.message ?: "Unknown error")
            }
        }
    }
}