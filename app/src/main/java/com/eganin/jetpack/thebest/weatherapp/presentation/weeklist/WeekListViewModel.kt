package com.eganin.jetpack.thebest.weatherapp.presentation.weeklist

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.location.LocationTracker
import com.example.domain.repository.WeatherRepository
import com.example.domain.util.Resource
import com.eganin.jetpack.thebest.weatherapp.common.presentation.getProviderLocation
import com.example.domain.repository.GeocodingRepository
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

    private val fakeData = Pair(first = 0.0, second = 0.0)

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

    private fun loadWeatherDataForEveryDay(searchQuery: String = "") {
        viewModelScope.launch {
            startLoadingState()

            val (data, fetchFromRemote) = provideDataForRepository(searchQuery = searchQuery)

            repository.getDataForEveryDay(
                lat = data.first, long = data.second, fetchFromRemote = fetchFromRemote
            ).collect { result ->
                wrapperForHandlerResource(result = result) {
                    state = state.copy(
                        info = it.map {
                            it.value.maxBy { it.temperatureCelsius }
                        },
                        isLoading = false,
                        error = null
                    )
                }
            }
        }
    }

    private fun <T> wrapperForHandlerResource(
        result: Resource<T>,
        onStateChangeSuccess: (T) -> Unit
    ) {
        when (result) {
            is Resource.Success -> {
                result.data?.let {
                    onStateChangeSuccess(it)
                }
            }

            is Resource.Error -> {
                errorLocationState()
            }

            is Resource.Loading -> {
                state = state.copy(isLoading = true)
            }
        }
    }

    private fun startLoadingState() {
        state = state.copy(
            isLoading = true,
            error = null,
        )
    }

    private fun errorLocationState() {
        onEvent(event = WeekListEvent.Error)
    }

    private suspend fun provideDataForRepository(searchQuery: String): Pair<Pair<Double, Double>, Boolean> {
        getProviderLocation(
            searchQuery = searchQuery,
            geocodingRepository = geocodingRepository,
            locationTracker = locationTracker
        )?.let { location ->
            return Pair(
                first = Pair(first = location.first, second = location.second),
                second = true
            )
        } ?: run {
            errorLocationState()
            return Pair(first = fakeData, second = false)
        }
    }
}