package com.example.domain.usecase.components

import com.example.domain.location.LocationTracker
import com.example.domain.repository.GeocodingRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProvideDataForRepository(
    private val geocodingRepository: GeocodingRepository,
    private val locationTracker: LocationTracker
) {

    private val fakeData = Pair(first = 0.0, second = 0.0)

    suspend operator fun invoke(searchQuery: String) =
        withContext(Dispatchers.IO) {
            val providerLocation = if (searchQuery.isNotEmpty()) {
                var coordinates: Pair<Double, Double>? = null
                geocodingRepository.getGeoFromCity(
                    cityName = searchQuery,
                    fetchFromRemote = true
                ).collect { answer ->
                    answer.data?.let {
                        coordinates = Pair(first = it.latitude, second = it.longitude)
                    }
                }
                coordinates
            } else {
                val answer = locationTracker.getCurrentLocation()
                answer?.let {
                    Pair(first = answer.latitude, second = answer.longitude)
                }
            }
            providerLocation?.let { location ->
                Pair(
                    first = Pair(first = location.first, second = location.second),
                    second = true
                )
            } ?: run {
                Pair(first = fakeData, second = false)
            }
        }
}
