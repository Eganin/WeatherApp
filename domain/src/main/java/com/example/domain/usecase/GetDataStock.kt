package com.example.domain.usecase

import com.example.domain.location.LocationTracker
import com.example.domain.repository.GeocodingRepository
import com.example.domain.repository.WeatherRepository
import com.example.domain.usecase.components.ProvideDataForRepository
import com.example.domain.util.Resource
import kotlinx.coroutines.flow.Flow

class GetDataStock(
    private val locationTracker: LocationTracker,
    private val geocodingRepository: GeocodingRepository,
    private val weatherRepository: WeatherRepository
) {

    suspend operator fun invoke(searchQuery: String): Flow<Resource<List<Int>>> {
        val (data, fetchFromRemote) = ProvideDataForRepository(
            locationTracker = locationTracker,
            geocodingRepository = geocodingRepository
        )(searchQuery = searchQuery)

        return weatherRepository.getDataForStock(
            lat = data.first,
            long = data.second,
            fetchFromRemote = fetchFromRemote
        )
    }
}