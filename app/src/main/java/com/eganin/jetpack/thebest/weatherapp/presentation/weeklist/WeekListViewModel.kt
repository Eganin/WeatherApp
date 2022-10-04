package com.eganin.jetpack.thebest.weatherapp.presentation.weeklist

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eganin.jetpack.thebest.weatherapp.presentation.citiespage.CitiesPageEvent
import com.example.domain.location.LocationTracker
import com.example.domain.repository.WeatherRepository
import com.example.domain.util.Resource
import com.eganin.jetpack.thebest.weatherapp.presentation.common.getProviderLocation
import com.example.domain.repository.GeocodingRepository
import com.example.domain.usecase.WeatherUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeekListViewModel @Inject constructor(
    private val weatherUseCases: WeatherUseCases
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
                    isLoading = false, error = event.message
                )
            }
        }
    }

    private fun loadWeatherDataForEveryDay(searchQuery: String = "") {
        viewModelScope.launch {
            startLoadingState()

            weatherUseCases.getDataForEveryDay(searchQuery = searchQuery).collect { result ->
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
                result.message?.let {
                    onEvent(event = WeekListEvent.Error(message = it))
                }?: run {
                    onEvent(event = WeekListEvent.Error(message = "Error"))
                }
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
}