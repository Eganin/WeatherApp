package com.example.domain.repository

import com.example.domain.models.sunsetsunrisetime.SunsetSunriseTimeData
import com.example.domain.util.Resource
import com.example.domain.models.weather.WeatherData
import com.example.domain.models.weather.WeatherInfo
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