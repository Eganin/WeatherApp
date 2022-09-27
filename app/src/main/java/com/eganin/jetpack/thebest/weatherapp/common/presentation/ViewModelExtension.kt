package com.eganin.jetpack.thebest.weatherapp.common.presentation

import com.eganin.jetpack.thebest.weatherapp.common.domain.location.LocationTracker
import com.eganin.jetpack.thebest.weatherapp.detailpage.domain.repository.GeocodingRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun getProviderLocation(
    searchQuery: String,
    geocodingRepository: GeocodingRepository,
    locationTracker: LocationTracker
) = withContext(Dispatchers.IO) {
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
    providerLocation
}