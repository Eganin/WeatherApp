package com.eganin.jetpack.thebest.weatherapp.detailpage.presentation.ui

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eganin.jetpack.thebest.weatherapp.common.domain.location.LocationTracker
import com.eganin.jetpack.thebest.weatherapp.common.domain.repository.WeatherRepository
import com.eganin.jetpack.thebest.weatherapp.common.domain.util.Resource
import com.eganin.jetpack.thebest.weatherapp.detailpage.domain.repository.GeocodingRepository
import com.eganin.jetpack.thebest.weatherapp.detailpage.domain.repository.SunsetSunriseTimeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: WeatherRepository,
    private val locationTracker: LocationTracker,
    private val geocodingRepository: GeocodingRepository,
    private val sunsetSunriseTimeRepository: SunsetSunriseTimeRepository
) : ViewModel() {

    var state by mutableStateOf(WeatherState())
        private set

    private var searchJob: Job? = null

    fun onEvent(event: DetailPageEvent) {
        when (event) {
            is DetailPageEvent.Refresh -> {
                if (state.searchQuery.isEmpty()) {
                    loadWeatherInfo()
                    loadDataStock()
                } else {
                    onEvent(event = DetailPageEvent.OnSearchQueryChange(query = state.searchQuery))
                }

            }

            is DetailPageEvent.OnSearchQueryChange -> {
                state = state.copy(searchQuery = event.query)
                searchJob?.cancel()
                searchJob = viewModelScope.launch {
                    delay(1000L)
                    loadGeocoding(cityName = state.searchQuery)
                }
            }

            is DetailPageEvent.FirstLoadDataFromCurrentGeolocation -> {
                loadWeatherInfo()
                loadDataStock()
                loadSunsetAndSunriseTimes()
            }

            is DetailPageEvent.Error -> {
                state = state.copy(
                    isLoading = false,
                    error = "Couldn't retrieve location.Make sure enable GPS"
                )
            }

            is DetailPageEvent.ErrorGeocodingFromCity -> {
                state = state.copy(
                    isLoading = false,
                    error = "Couldn't retrieve location. Wrong city"
                )
            }
        }
    }

    private fun loadWeatherInfo() {
        viewModelScope.launch {
            state = state.copy(
                isLoading = true,
                error = null,
            )
            locationTracker.getCurrentLocation()?.let { location ->
                when (val result =
                    repository.getWeatherData(location.latitude, location.longitude)) {

                    is Resource.Success -> {
                        state = state.copy(
                            weatherInfo = result.data,
                            isLoading = false,
                            error = null
                        )
                    }
                    is Resource.Error -> {
                        state = state.copy(
                            weatherInfo = null,
                            isLoading = false,
                            error = result.message
                        )
                    }
                }
            } ?: run {
                onEvent(event = DetailPageEvent.Error)
            }
        }
    }

    private fun loadDataStock() {
        viewModelScope.launch {
            state = state.copy(
                isLoading = true,
                error = null,
            )

            val providerLocation = if (state.searchQuery.isNotEmpty()) {
                val answer = geocodingRepository.getGeoFromCity(cityName = state.searchQuery).data
                answer?.let {
                    Pair(first = answer.latitude, second = answer.longitude)
                }
            } else {
                val answer = locationTracker.getCurrentLocation()
                answer?.let {
                    Pair(first = answer.latitude, second = answer.longitude)
                }
            }

            providerLocation?.let { location ->
                when (val result =
                    repository.getDataForStock(location.first, location.second)) {
                    is Resource.Success -> {
                        state = state.copy(
                            dataStock = result.data,
                            isLoading = false,
                            error = null
                        )
                    }
                    is Resource.Error -> {
                        state = state.copy(
                            dataStock = null,
                            isLoading = false,
                            error = result.message
                        )
                    }
                }
            } ?: run {
                onEvent(event = DetailPageEvent.Error)
            }
        }
    }

    private fun loadGeocoding(cityName: String) {
        searchJob = viewModelScope.launch {
            state = state.copy(
                isLoading = true,
                error = null,
            )
            // load data for stock widget
            loadDataStock()
            // load SunsetAndSunrise for dynamic weather section
            loadSunsetAndSunriseTimes()
            // get geocoding from city
            geocodingRepository.getGeoFromCity(cityName = cityName).data?.let { location ->
                // get weather info
                when (val result =
                    repository.getWeatherData(location.latitude, location.longitude)) {

                    is Resource.Success -> {
                        state = state.copy(
                            weatherInfo = result.data,
                            isLoading = false,
                            error = null
                        )
                    }
                    is Resource.Error -> {
                        state = state.copy(
                            weatherInfo = null,
                            isLoading = false,
                            error = result.message
                        )
                    }
                }
            } ?: run {
                onEvent(event = DetailPageEvent.ErrorGeocodingFromCity)
            }
        }

    }

    private fun loadSunsetAndSunriseTimes() {
        viewModelScope.launch {
            state = state.copy(
                isLoading = true,
                error = null,
            )

            val providerLocation = if (state.searchQuery.isNotEmpty()) {
                val answer = geocodingRepository.getGeoFromCity(cityName = state.searchQuery).data
                answer?.let {
                    Pair(first = answer.latitude, second = answer.longitude)
                }
            } else {
                val answer = locationTracker.getCurrentLocation()
                answer?.let {
                    Pair(first = answer.latitude, second = answer.longitude)
                }
            }

            providerLocation?.let { location ->
                when (val result =
                    sunsetSunriseTimeRepository.getSunsetSunriseTime(
                        lat = location.first,
                        lon = location.second
                    )) {

                    is Resource.Success -> {
                        state = state.copy(
                            sunsetAndSunriseTime = result.data,
                            isLoading = false,
                            error = null
                        )
                    }
                    is Resource.Error -> {
                        state = state.copy(
                            sunsetAndSunriseTime = null,
                            isLoading = false,
                            error = result.message
                        )
                    }
                }
            } ?: run {
                onEvent(event = DetailPageEvent.Error)
            }
        }
    }
}