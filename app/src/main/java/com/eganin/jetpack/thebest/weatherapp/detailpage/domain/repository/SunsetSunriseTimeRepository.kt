package com.eganin.jetpack.thebest.weatherapp.detailpage.domain.repository

interface SunsetSunriseTimeRepository {
    suspend fun getSunsetSunriseTime(lat : Double, lon : Double)
}