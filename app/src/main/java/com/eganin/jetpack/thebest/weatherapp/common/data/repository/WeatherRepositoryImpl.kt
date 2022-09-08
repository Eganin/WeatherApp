package com.eganin.jetpack.thebest.weatherapp.common.data.repository

import com.eganin.jetpack.thebest.weatherapp.common.data.mapper.toAverageValues
import com.eganin.jetpack.thebest.weatherapp.common.data.mapper.toWeatherDataMap
import com.eganin.jetpack.thebest.weatherapp.common.data.remote.WeatherApi
import com.eganin.jetpack.thebest.weatherapp.common.data.mapper.toWeatherInfo
import com.eganin.jetpack.thebest.weatherapp.common.domain.repository.WeatherRepository
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

    override suspend fun getDataForStock(lat: Double, long: Double): Resource<List<Int>> {
        return withContext(Dispatchers.IO) {
            try {
                Resource.Success(
                    data = api.getWeather(
                        lat = lat,
                        long = long
                    ).toWeatherInfo().weatherDataPerDay.get(0)?.toAverageValues()
                )
            } catch (e: Exception) {
                e.printStackTrace()
                Resource.Error(message = e.message ?: "Unknown error")
            }
        }
    }

    override suspend fun getDataForEveryDay(
        lat: Double,
        long: Double
    ): Resource<Map<Int, List<WeatherData>>> {
        return withContext(Dispatchers.IO){
            try{
                Resource.Success(
                    data = api.getWeather(
                        lat = lat,
                        long = long
                    ).weatherData.toWeatherDataMap()
                )
            }catch (e : Exception){
                e.printStackTrace()
                Resource.Error(message = e.message ?: "Unknown error")
            }
        }
    }
}