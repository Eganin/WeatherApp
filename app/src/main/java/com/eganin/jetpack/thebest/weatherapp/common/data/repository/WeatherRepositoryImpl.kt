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
import com.eganin.jetpack.thebest.weatherapp.data.local.WeatherDatabase
import com.eganin.jetpack.thebest.weatherapp.data.local.entities.DataForStockEntity
import com.eganin.jetpack.thebest.weatherapp.data.mapper.toWeatherDataDto
import com.eganin.jetpack.thebest.weatherapp.data.mapper.toWeatherDataEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val api: WeatherApi,
    db: WeatherDatabase
) : WeatherRepository {

    private val weatherDataDao = db.weatherDataDao
    private val dataForStockDao = db.dataForStockDao
    override suspend fun getWeatherData(lat: Double, long: Double): Resource<WeatherInfo> {
        return getDataForRepository(
            data = api.getWeather(
                lat = lat,
                long = long
            ).toWeatherInfo()
        )
    }

    override suspend fun getDataForStock(lat: Double, long: Double): Resource<List<Int>> {

        val remoteData = api.getWeather(
            lat = lat,
            long = long
        ).toWeatherInfo().weatherDataPerDay[0]?.toAverageValues()

        remoteData?.let {
            dataForStockDao.clearDataForStockEntity()
            dataForStockDao.insertDataForStock(dataForStockEntity = DataForStockEntity(data = it))
        }

        return getDataForRepository(
            data = dataForStockDao.getDataDorStockEntity().data
        )
    }

    override suspend fun getDataForEveryDay(
        lat: Double,
        long: Double
    ): Resource<Map<Int, List<WeatherData>>> {

        val remoteResult = api.getWeather(
            lat = lat,
            long = long
        ).weatherData

        weatherDataDao.clearWeatherData()
        weatherDataDao.insertWeatherData(weatherData = remoteResult.toWeatherDataEntity())

        return getDataForRepository(
            data = weatherDataDao.getWeatherDataInfo().toWeatherDataDto().toWeatherDataMap()
        )
    }
}