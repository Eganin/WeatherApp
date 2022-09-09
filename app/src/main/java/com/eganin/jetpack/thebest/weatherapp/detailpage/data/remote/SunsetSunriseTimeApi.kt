package com.eganin.jetpack.thebest.weatherapp.detailpage.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface SunsetSunriseTimeApi {
    @GET("json?date=today")
    suspend fun getTimesData(
        @Query("lat") lat: Double,
        @Query("lng") long: Double,
    ): SunsetSunriseTimeDto
}