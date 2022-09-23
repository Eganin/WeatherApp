package com.eganin.jetpack.thebest.weatherapp.weeklist

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eganin.jetpack.thebest.weatherapp.common.domain.location.LocationTracker
import com.eganin.jetpack.thebest.weatherapp.common.domain.repository.WeatherRepository
import com.eganin.jetpack.thebest.weatherapp.common.domain.util.Resource
import com.eganin.jetpack.thebest.weatherapp.detailpage.domain.repository.GeocodingRepository
import com.eganin.jetpack.thebest.weatherapp.detailpage.presentation.ui.WeatherState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class WeekListViewModel @Inject constructor(
    private val repository: WeatherRepository,
    private val locationTracker: LocationTracker,
    private val geocodingRepository: GeocodingRepository
) : ViewModel() {
    var state by mutableStateOf(WeekListState())
        private set

    fun onEvent(event: WeekListEvent) {
        when (event) {
            is WeekListEvent.LoadData -> {
                loadWeatherDataForEveryDay(searchQuery = event.searchQuery)
            }

            is WeekListEvent.Error -> {
                state = state.copy(
                    isLoading = false, error = "Couldn't retrieve location.Make sure enable GPS"
                )
            }
        }
    }

    private fun <T> loadDataUsingResource(
        stateError: WeekListState,
        stateSuccess: WeekListState,
        result: Resource<T>,
    ) {
        viewModelScope.launch {
            state = when (result) {
                is Resource.Success -> {
                    stateSuccess
                }
                is Resource.Error -> {
                    stateError
                }
            }
        }
    }

    private suspend fun getProviderLocation(searchQuery: String) = withContext(Dispatchers.IO) {
        val providerLocation = if (searchQuery.isNotEmpty()) {
            val answer = geocodingRepository.getGeoFromCity(cityName = searchQuery, fetchFromRemote = true).data
            answer?.let {
                Pair(first = answer.latitude, second = answer.longitude)
            }
        } else {
            val answer = locationTracker.getCurrentLocation()
            answer?.let {
                Pair(first = answer.latitude, second = answer.longitude)
            }
        }
        providerLocation
    }

    private fun loadWeatherDataForEveryDay(searchQuery: String = "") {
        viewModelScope.launch {
            state = state.copy(
                isLoading = true,
                error = null,
            )

            getProviderLocation(searchQuery = searchQuery)?.let { location ->
                val result = repository.getDataForEveryDay(
                    lat = location.first, long = location.second, fetchFromRemote = true
                )
                loadDataUsingResource(
                    result = result, stateSuccess = state.copy(
                        info = result.data?.map {
                            it.value.maxBy { it.temperatureCelsius }
                        },
                        isLoading = false,
                        error = null
                    ), stateError = state.copy(
                        info = null,
                        isLoading = false,
                        error = result.message
                    )
                )

            } ?: run {
                onEvent(event = WeekListEvent.Error)
            }
        }
    }
}