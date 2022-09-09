package com.eganin.jetpack.thebest.weatherapp.detailpage.data.repository

import android.util.Log
import com.eganin.jetpack.thebest.weatherapp.detailpage.data.remote.SunsetSunriseTimeApi
import com.eganin.jetpack.thebest.weatherapp.detailpage.domain.repository.SunsetSunriseTimeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class SunsetSunriseTimeRepositoryImpl @Inject constructor(
    private val api : SunsetSunriseTimeApi
) : SunsetSunriseTimeRepository{
    override suspend fun getSunsetSunriseTime(lat: Double, lon: Double) {
        withContext(Dispatchers.IO){
            val res = api.getTimesData(lat = lat, long = lon).results
            Log.d("EEE",res.toString())
        }
    }
}