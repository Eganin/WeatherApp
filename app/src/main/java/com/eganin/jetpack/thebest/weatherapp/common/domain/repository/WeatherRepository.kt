package com.eganin.jetpack.thebest.weatherapp.common.domain.repository

import com.eganin.jetpack.thebest.weatherapp.common.domain.util.Resource
import com.eganin.jetpack.thebest.weatherapp.common.domain.weather.WeatherData
import com.eganin.jetpack.thebest.weatherapp.common.domain.weather.WeatherInfo
import com.eganin.jetpack.thebest.weatherapp.detailpage.domain.sunsetsunrisetime.SunsetSunriseTimeData
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    suspend fun getWeatherData(
        lat: Double, long: Double, fetchFromRemote: Boolean
    ): Flow<Resource<WeatherInfo>>

    suspend fun getDataForStock(
        lat: Double, long: Double, fetchFromRemote: Boolean
    ): Flow<Resource<List<Int>>>

    suspend fun getDataForEveryDay(
        lat: Double, long: Double, fetchFromRemote: Boolean
    ): Flow<Resource<Map<Int, List<WeatherData>>>>

    suspend fun getDataForCitiesPage(
        cityName: String,
        fetchFromRemote: Boolean
    ): Flow<Resource<List<Triple<WeatherData?, SunsetSunriseTimeData?,String>>>>

    suspend fun getCityNameFromDB() : Flow<Resource<Set<String>>>
}