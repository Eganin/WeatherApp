package com.eganin.jetpack.thebest.weatherapp.common.data.repository

import com.eganin.jetpack.thebest.weatherapp.common.data.local.WeatherDatabase
import com.eganin.jetpack.thebest.weatherapp.common.data.local.entities.DataForStockEntity
import com.eganin.jetpack.thebest.weatherapp.common.data.mapper.*
import com.eganin.jetpack.thebest.weatherapp.common.data.remote.WeatherApi
import com.eganin.jetpack.thebest.weatherapp.common.domain.repository.WeatherRepository
import com.eganin.jetpack.thebest.weatherapp.common.domain.util.Resource
import com.eganin.jetpack.thebest.weatherapp.common.domain.weather.WeatherData
import com.eganin.jetpack.thebest.weatherapp.common.domain.weather.WeatherInfo
import com.eganin.jetpack.thebest.weatherapp.detailpage.domain.repository.GeocodingRepository
import com.eganin.jetpack.thebest.weatherapp.detailpage.domain.repository.SunsetSunriseTimeRepository
import com.eganin.jetpack.thebest.weatherapp.detailpage.domain.sunsetsunrisetime.SunsetSunriseTimeData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val api: WeatherApi,
    private val geocodingRepository: GeocodingRepository,
    private val sunsetSunriseTimeRepository: SunsetSunriseTimeRepository,
    db: WeatherDatabase
) : WeatherRepository {

    private val weatherDataDao = db.weatherDataDao
    private val dataForStockDao = db.dataForStockDao
    private val weatherInfoDao = db.weatherInfoDao
    private val cityDataDao = db.cityDataDao

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
                dataForStockDao.getDataForStockEntity().data
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

    override suspend fun getDataForCitiesPage(
        cityName: String,
        fetchFromRemote: Boolean
    ): Flow<Resource<List<Triple<WeatherData?, SunsetSunriseTimeData?,String>>>> {

        var weatherData: WeatherData? = null
        var sunsetSunrise: SunsetSunriseTimeData? = null

        return flow {
            bodyForDataLoading {
                if (fetchFromRemote) {
                    geocodingRepository.getGeoFromCity(
                        cityName = cityName,
                        fetchFromRemote = fetchFromRemote
                    ).collect { geocodingDtoResource ->
                        geocodingDtoResource.data?.let { geocodingDto ->
                            sunsetSunriseTimeRepository.getSunsetSunriseTime(
                                lat = geocodingDto.latitude,
                                lon = geocodingDto.longitude,
                                fetchFromRemote = fetchFromRemote
                            ).collect { sunsetSunriseResource ->
                                sunsetSunriseResource.data?.let {
                                    sunsetSunrise = it
                                }
                            }

                            getWeatherData(
                                geocodingDto.latitude,
                                geocodingDto.longitude,
                                fetchFromRemote = true
                            ).collect { weatherInfoResource ->
                                weatherInfoResource.data?.currentWeatherData?.let { currentWeatherData ->
                                    weatherData = currentWeatherData
                                }
                            }
                        }

                    }
                    cityDataDao.insertCityData(
                        data = Triple(
                            first = weatherData,
                            second = sunsetSunrise,
                            third = cityName
                        ).toCityDataEntity()
                    )
                }
                cityDataDao.getCityData().map { it.toTriple() }
            }
        }
    }

    override suspend fun getCityNameFromDB(): Flow<Resource<Set<String>>> {
        return flow{
            bodyForDataLoading {
                cityDataDao.getCityData().map { it.cityName }.toSet()
            }
        }
    }


}