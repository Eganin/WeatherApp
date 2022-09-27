package com.eganin.jetpack.thebest.weatherapp.common.data.repository

import com.eganin.jetpack.thebest.weatherapp.common.data.local.WeatherDatabase
import com.eganin.jetpack.thebest.weatherapp.common.data.local.entities.DataForStockEntity
import com.eganin.jetpack.thebest.weatherapp.common.data.mapper.*
import com.eganin.jetpack.thebest.weatherapp.common.data.remote.WeatherApi
import com.eganin.jetpack.thebest.weatherapp.common.domain.repository.WeatherRepository
import com.eganin.jetpack.thebest.weatherapp.common.domain.util.Resource
import com.eganin.jetpack.thebest.weatherapp.common.domain.weather.WeatherData
import com.eganin.jetpack.thebest.weatherapp.common.domain.weather.WeatherInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val api: WeatherApi,
    db: WeatherDatabase
) : WeatherRepository {

    private val weatherDataDao = db.weatherDataDao
    private val dataForStockDao = db.dataForStockDao
    private val weatherInfoDao = db.weatherInfoDao

    override suspend fun getWeatherData(
        lat: Double,
        long: Double,
        fetchFromRemote: Boolean
    ): Flow<Resource<WeatherInfo>> {

        return flow {
            bodyForDataLoading {
                if (fetchFromRemote) {
                    val remoteData = api.getWeather(
                        lat = lat,
                        long = long
                    ).toWeatherInfo()

                    weatherInfoDao.clearWeatherInfo()
                    weatherInfoDao.insertWeatherInfo(
                        weatherInfoEntity = remoteData.toWeatherInfoEntity()
                    )
                }
                weatherInfoDao.getWeatherInfo().toWeatherInfo()
            }
        }
    }

    override suspend fun getDataForStock(
        lat: Double,
        long: Double,
        fetchFromRemote: Boolean
    ): Flow<Resource<List<Int>>> {
        return flow {
            bodyForDataLoading {
                if (fetchFromRemote) {
                    val remoteData = api.getWeather(
                        lat = lat,
                        long = long
                    ).toWeatherInfo().weatherDataPerDay[0]?.toAverageValues()

                    remoteData?.let {
                        dataForStockDao.clearDataForStockEntity()
                        dataForStockDao.insertDataForStock(
                            dataForStockEntity = DataForStockEntity(
                                data = it
                            )
                        )
                    }
                }
                dataForStockDao.getDataDorStockEntity().data
            }
        }
    }

    override suspend fun getDataForEveryDay(
        lat: Double,
        long: Double,
        fetchFromRemote: Boolean
    ): Flow<Resource<Map<Int, List<WeatherData>>>> {

        return flow {
            bodyForDataLoading {
                if (fetchFromRemote) {
                    val remoteResult = api.getWeather(
                        lat = lat,
                        long = long
                    ).weatherData

                    weatherDataDao.clearWeatherData()
                    weatherDataDao.insertWeatherData(weatherData = remoteResult.toWeatherDataEntity())
                }
                weatherDataDao.getWeatherDataInfo().toWeatherDataDto().toWeatherDataMap()
            }
        }
    }
}