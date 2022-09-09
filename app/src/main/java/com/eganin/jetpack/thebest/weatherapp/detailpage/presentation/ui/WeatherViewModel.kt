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
import com.eganin.jetpack.thebest.weatherapp.common.domain.weather.WeatherInfo
import com.eganin.jetpack.thebest.weatherapp.detailpage.data.remote.GeocodingDto
import com.eganin.jetpack.thebest.weatherapp.detailpage.domain.repository.GeocodingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: WeatherRepository,
    private val locationTracker: LocationTracker,
    private val geocodingRepository: GeocodingRepository
) : ViewModel() {

    var state by mutableStateOf(WeatherState())
        private set

    private var searchJob: Job? = null

    fun loadWeatherInfo() {
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
                Log.d("EEE", "FAILED")
                state = state.copy(
                    isLoading = false,
                    error = "Couldn't retrieve location.Make sure enable GPS"
                )
            }
        }
    }

    fun loadDataStock() {
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
                Log.d("EEE", "FAILED")
                state = state.copy(
                    isLoading = false,
                    error = "Couldn't retrieve location.Make sure enable GPS"
                )
            }
        }
    }

    fun loadGeocoding(cityName: String) {
        state = state.copy(searchQuery = cityName)
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(1000L)
            state = state.copy(
                isLoading = true,
                error = null,
            )
            // load data for stock widget
            loadDataStock()
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
                Log.d("EEE", "FAILED")
                state = state.copy(
                    isLoading = false,
                    error = "Couldn't retrieve location. Wrong city"
                )
            }
        }

    }
}