package com.eganin.jetpack.thebest.weatherapp.domain.repository

import com.eganin.jetpack.thebest.weatherapp.domain.util.Resource
import com.eganin.jetpack.thebest.weatherapp.domain.weather.WeatherInfo

interface WeatherRepository {
    suspend fun getWeatherData(lat: Double, long: Double): Resource<WeatherInfo>
}