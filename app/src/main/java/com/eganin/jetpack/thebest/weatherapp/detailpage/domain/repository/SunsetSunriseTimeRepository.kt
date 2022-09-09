package com.eganin.jetpack.thebest.weatherapp.detailpage.domain.repository

import com.eganin.jetpack.thebest.weatherapp.common.domain.util.Resource
import com.eganin.jetpack.thebest.weatherapp.detailpage.domain.sunsetsunrisetime.SunsetSunriseTimeData

interface SunsetSunriseTimeRepository {
    suspend fun getSunsetSunriseTime(lat : Double, lon : Double) : Resource<SunsetSunriseTimeData>
}