package com.eganin.jetpack.thebest.weatherapp.data.repository

import com.eganin.jetpack.thebest.weatherapp.data.remote.WeatherApi
import com.eganin.jetpack.thebest.weatherapp.data.rmapper.toWeatherInfo
import com.eganin.jetpack.thebest.weatherapp.domain.repository.WeatherRepository
import com.eganin.jetpack.thebest.weatherapp.domain.util.Resource
import com.eganin.jetpack.thebest.weatherapp.domain.weather.WeatherInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val api: WeatherApi
) : WeatherRepository {
    override suspend fun getWeatherData(lat: Double, long: Double): Resource<WeatherInfo> {
        return withContext(Dispatchers.IO) {
            try {
                Resource.Success(
                    data = api.getWeather(
                        lat = lat,
                        long = long
                    ).toWeatherInfo()
                )
            } catch (e: Exception) {
                e.printStackTrace()
                Resource.Error(message = e.message ?: "Unknown error")
            }
        }
    }
}