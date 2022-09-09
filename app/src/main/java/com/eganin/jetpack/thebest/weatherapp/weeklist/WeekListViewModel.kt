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
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
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
                    isLoading = false,
                    error = "Couldn't retrieve location.Make sure enable GPS"
                )
            }
        }
    }

    private fun loadWeatherDataForEveryDay(searchQuery: String = "") {
        viewModelScope.launch {
            state = state.copy(
                isLoading = true,
                error = null,
            )

            val providerLocation = if (searchQuery.isNotEmpty()) {
                val answer = geocodingRepository.getGeoFromCity(cityName = searchQuery).data
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
                    repository.getDataForEveryDay(
                        lat = location.first,
                        long = location.second
                    )) {
                    is Resource.Success -> {
                        state = state.copy(
                            info = result.data,
                            isLoading = false,
                            error = null
                        )
                    }
                    is Resource.Error -> {
                        state = state.copy(
                            info = null,
                            isLoading = false,
                            error = result.message
                        )
                    }
                }
            } ?: run {
                onEvent(event = WeekListEvent.Error)
            }
        }
    }
}