package com.eganin.jetpack.thebest.weatherapp.detailpage.domain.repository

import com.eganin.jetpack.thebest.weatherapp.detailpage.domain.util.Resource
import com.eganin.jetpack.thebest.weatherapp.detailpage.domain.weather.WeatherInfo

interface WeatherRepository {
    suspend fun getWeatherData(lat: Double, long: Double): Resource<WeatherInfo>
    suspend fun getDataForStock(lat: Double, long: Double) : Resource<List<Int>>
}