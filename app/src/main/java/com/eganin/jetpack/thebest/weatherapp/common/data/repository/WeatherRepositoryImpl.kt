package com.eganin.jetpack.thebest.weatherapp.common.data.repository

import com.eganin.jetpack.thebest.weatherapp.common.data.mapper.toAverageValues
import com.eganin.jetpack.thebest.weatherapp.common.data.mapper.toWeatherDataMap
import com.eganin.jetpack.thebest.weatherapp.common.data.remote.WeatherApi
import com.eganin.jetpack.thebest.weatherapp.common.data.mapper.toWeatherInfo
import com.eganin.jetpack.thebest.weatherapp.common.domain.repository.WeatherRepository
import com.eganin.jetpack.thebest.weatherapp.common.domain.repository.getDataForRepository
import com.eganin.jetpack.thebest.weatherapp.common.domain.util.Resource
import com.eganin.jetpack.thebest.weatherapp.common.domain.weather.WeatherData
import com.eganin.jetpack.thebest.weatherapp.common.domain.weather.WeatherInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val api: WeatherApi
) : WeatherRepository {
    override suspend fun getWeatherData(lat: Double, long: Double): Resource<WeatherInfo> {
        return getDataForRepository(
            data = api.getWeather(
                lat = lat,
                long = long
            ).toWeatherInfo()
        )
    }

    override suspend fun getDataForStock(lat: Double, long: Double): Resource<List<Int>> {
        return getDataForRepository(
            data = api.getWeather(
                lat = lat,
                long = long
            ).toWeatherInfo().weatherDataPerDay[0]?.toAverageValues()
        )
    }

    override suspend fun getDataForEveryDay(
        lat: Double,
        long: Double
    ): Resource<Map<Int, List<WeatherData>>> {
        return getDataForRepository(
            data = api.getWeather(
                lat = lat,
                long = long
            ).weatherData.toWeatherDataMap()
        )
    }
}