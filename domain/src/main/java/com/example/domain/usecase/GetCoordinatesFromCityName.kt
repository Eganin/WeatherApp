package com.example.domain.usecase

import com.example.domain.location.LocationTracker
import com.example.domain.models.weather.WeatherInfo
import com.example.domain.repository.GeocodingRepository
import com.example.domain.repository.WeatherRepository
import com.example.domain.usecase.components.ProvideDataForRepository
import com.example.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetCoordinatesFromCityName(
    private val locationTracker: LocationTracker,
    private val geocodingRepository: GeocodingRepository,
    private val weatherRepository: WeatherRepository
) {

    suspend operator fun invoke(
        searchQuery: String,
        cityName: String
    ): Flow<Flow<Resource<WeatherInfo>>> {
        val (data, fetchFromRemote) = ProvideDataForRepository(
            locationTracker = locationTracker,
            geocodingRepository = geocodingRepository
        )(searchQuery = searchQuery)

        return geocodingRepository.getGeoFromCity(
            cityName = cityName,
            fetchFromRemote = fetchFromRemote
        ).map {
            data.let { location ->
                weatherRepository.getWeatherData(
                    lat = location.first,
                    long = location.second,
                    fetchFromRemote = fetchFromRemote
                )
            }
        }

    }
}