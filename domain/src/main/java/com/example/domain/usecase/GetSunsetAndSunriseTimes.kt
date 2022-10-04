package com.example.domain.usecase

import com.example.domain.location.LocationTracker
import com.example.domain.models.sunsetsunrisetime.SunsetSunriseTimeData
import com.example.domain.repository.GeocodingRepository
import com.example.domain.repository.SunsetSunriseTimeRepository
import com.example.domain.usecase.components.ProvideDataForRepository
import com.example.domain.util.Resource
import kotlinx.coroutines.flow.Flow

class GetSunsetAndSunriseTimes(
    private val locationTracker: LocationTracker,
    private val geocodingRepository: GeocodingRepository,
    private val sunsetSunriseTimeRepository: SunsetSunriseTimeRepository
) {

    suspend operator fun invoke(searchQuery : String): Flow<Resource<SunsetSunriseTimeData>> {
        val (data, fetchFromRemote) = ProvideDataForRepository(
            locationTracker = locationTracker,
            geocodingRepository = geocodingRepository
        )(searchQuery = searchQuery)

        return sunsetSunriseTimeRepository.getSunsetSunriseTime(
            lat = data.first,
            lon = data.second,
            fetchFromRemote = fetchFromRemote
        )
    }
}