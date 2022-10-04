package com.example.domain.usecase

import com.example.domain.models.sunsetsunrisetime.SunsetSunriseTimeData
import com.example.domain.models.weather.WeatherData
import com.example.domain.repository.WeatherRepository
import com.example.domain.util.Resource
import kotlinx.coroutines.flow.Flow

class GetDataForCitiesPage(
    private val weatherRepository: WeatherRepository
) {

    operator fun invoke(
        cityName: String,
        fetchFromRemote: Boolean
    ): Flow<Resource<List<Triple<WeatherData?, SunsetSunriseTimeData?, String>>>> {
        return weatherRepository.getDataForCitiesPage(
            cityName = cityName,
            fetchFromRemote = fetchFromRemote
        )
    }
}