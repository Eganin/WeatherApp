package com.eganin.jetpack.thebest.weatherapp.presentation.detailpage

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.location.LocationTracker
import com.example.domain.repository.GeocodingRepository
import com.example.domain.repository.SunsetSunriseTimeRepository
import com.example.domain.repository.WeatherRepository
import com.example.domain.usecase.WeatherUseCases
import com.example.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val weatherUseCases: WeatherUseCases
) : ViewModel() {

    var citiesItemList by mutableStateOf(listOf<String>())

    var state by mutableStateOf(WeatherState())
        private set

    private var searchJob: Job? = null

    private var listSearchQuery = mutableSetOf<String>()

    init {
        viewModelScope.launch {
            weatherUseCases.getCity().collect { result ->
                wrapperForHandlerResource(result = result, onStateChangeSuccess = {
                    if (it.isNotEmpty()) listSearchQuery = it as MutableSet<String>
                })
            }
        }
    }

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
                state = state.copy(searchQuery = event.query.trim())
                searchJob?.cancel()
                searchJob = viewModelScope.launch {
                    delay(2000L)
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
                    error = event.message
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

    fun getLastCity() = if (citiesItemList.isNotEmpty()) citiesItemList.last() else ""

    private fun loadWeatherInfo() {
        viewModelScope.launch {
            startLoadingState()

            weatherUseCases.getWeatherInfo(searchQuery = state.searchQuery).collect { result ->
                wrapperForHandlerResource(result = result, onStateChangeSuccess = {
                    state = state.copy(
                        weatherInfo = it,
                        isLoading = false,
                        error = null
                    )
                })
            }
        }
    }

    private fun loadDataStock() {
        viewModelScope.launch {
            startLoadingState()
            weatherUseCases.getDataStock(searchQuery = state.searchQuery).collect { result ->
                wrapperForHandlerResource(result = result, onStateChangeSuccess = {
                    state = state.copy(
                        dataStock = it,
                        isLoading = false,
                        error = null
                    )
                })
            }
        }
    }

    private fun loadGeocoding(cityName: String) {
        searchJob = viewModelScope.launch {
            startLoadingState()
            // load data for stock widget
            loadDataStock()
            // load SunsetAndSunrise for dynamic weather section
            loadSunsetAndSunriseTimes()

            weatherUseCases.getCoordinatesFromCity(
                searchQuery = state.searchQuery,
                cityName = cityName
            ).collect {
                it.collect { result ->
                    wrapperForHandlerResource(result = result, onStateChangeSuccess = {
                        state = state.copy(
                            weatherInfo = result.data,
                            isLoading = false,
                            error = null
                        )
                    }) {
                        if (listSearchQuery.contains(state.searchQuery)) {
                            listSearchQuery.remove(state.searchQuery)
                        }
                        listSearchQuery.add(state.searchQuery)
                        citiesItemList = listSearchQuery.toList()
                    }
                }
            }
        }
    }

    private fun loadSunsetAndSunriseTimes() {
        viewModelScope.launch {
            startLoadingState()
            weatherUseCases.getSunsetAndSunriseTimes(searchQuery = state.searchQuery)
                .collect { result ->
                    wrapperForHandlerResource(result = result, onStateChangeSuccess = {
                        state =
                            state.copy(sunsetAndSunriseTime = it, isLoading = false, error = null)
                    })
                }
        }
    }

    private fun <T> wrapperForHandlerResource(
        result: Resource<T>,
        onStateChangeSuccess: (T) -> Unit,
        successAction: (() -> Unit)? = null
    ) {
        when (result) {
            is Resource.Success -> {
                successAction?.invoke()
                result.data?.let {
                    onStateChangeSuccess(it)
                }
            }

            is Resource.Error -> {
                result.message?.let {
                    onEvent(event = DetailPageEvent.Error(message = it))
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