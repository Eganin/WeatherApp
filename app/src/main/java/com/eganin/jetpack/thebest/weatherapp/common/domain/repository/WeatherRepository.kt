package com.eganin.jetpack.thebest.weatherapp.common.domain.repository

import com.eganin.jetpack.thebest.weatherapp.common.domain.util.Resource
import com.eganin.jetpack.thebest.weatherapp.common.domain.weather.WeatherData
import com.eganin.jetpack.thebest.weatherapp.common.domain.weather.WeatherInfo

interface WeatherRepository {
    suspend fun getWeatherData(lat: Double, long: Double): Resource<WeatherInfo>
    suspend fun getDataForStock(lat: Double, long: Double,fetchFromRemote: Boolean) : Resource<List<Int>>
    suspend fun getDataForEveryDay(lat: Double,long: Double,fetchFromRemote: Boolean) : Resource<Map<Int, List<WeatherData>>>
}