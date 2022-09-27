package com.eganin.jetpack.thebest.weatherapp.detailpage.presentation.ui

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
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: WeatherRepository,
    private val locationTracker: LocationTracker,
    private val geocodingRepository: GeocodingRepository,
    private val sunsetSunriseTimeRepository: SunsetSunriseTimeRepository
) : ViewModel() {

    var citiesItemList by mutableStateOf(listOf<String>())

    var state by mutableStateOf(WeatherState())
        private set

    private var searchJob: Job? = null

    private val listSearchQuery = mutableSetOf<String>()

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
                    delay(1500L)
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

    fun getLastCity() = if (citiesItemList.isNotEmpty()) citiesItemList.last() else ""

    private fun <T> loadDataUsingResource(
        stateError: WeatherState,
        stateSuccess: WeatherState,
        result: Resource<T>,
        successAction: (() -> Unit)? = null
    ) {
        viewModelScope.launch {
            state = when (result) {
                is Resource.Success -> {
                    successAction?.invoke()
                    stateSuccess
                }
                is Resource.Error -> {
                    stateError
                }
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
        onEvent(event = DetailPageEvent.Error)
    }

    private suspend fun getProviderLocation() = withContext(Dispatchers.IO) {
        val providerLocation = if (state.searchQuery.isNotEmpty()) {
            val answer = geocodingRepository.getGeoFromCity(
                cityName = state.searchQuery,
                fetchFromRemote = true
            ).data
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

    private fun loadWeatherInfo() {
        viewModelScope.launch {
            startLoadingState()
            locationTracker.getCurrentLocation()?.let { location ->
                val result = repository.getWeatherData(
                    location.latitude,
                    location.longitude,
                    fetchFromRemote = true
                )
                loadDataUsingResource(
                    stateSuccess = state.copy(
                        weatherInfo = result.data,
                        isLoading = false,
                        error = null
                    ),
                    stateError = state.copy(
                        weatherInfo = null,
                        isLoading = false,
                        error = result.message
                    ),
                    result = result
                )
            } ?: run {
                onEvent(event = DetailPageEvent.Error)
            }
        }
    }

    private fun loadDataStock() {
        viewModelScope.launch {
            startLoadingState()

            var result: Resource<List<Int>>? = null
            getProviderLocation()?.let { location ->
                result =
                    repository.getDataForStock(
                        location.first,
                        location.second,
                        fetchFromRemote = true
                    )
            } ?: run {
                val fakeData = Pair(first = 0.0, second = 0.0)
                result =
                    repository.getDataForStock(
                        fakeData.first,
                        fakeData.second,
                        fetchFromRemote = false
                    )
                errorLocationState()
            }

            result?.let {
                loadDataUsingResource(
                    result = it,
                    stateSuccess = state.copy(
                        dataStock = it.data,
                        isLoading = false,
                        error = null
                    ),
                    stateError = state.copy(
                        dataStock = null,
                        isLoading = false,
                        error = it.message
                    )
                )
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
            // get geocoding from city
            geocodingRepository.getGeoFromCity(
                cityName = cityName,
                fetchFromRemote = true
            ).data?.let { location ->
                // get weather info
                val result =
                    repository.getWeatherData(
                        location.latitude,
                        location.longitude,
                        fetchFromRemote = true
                    )
                loadDataUsingResource(
                    result = result,
                    stateSuccess = state.copy(
                        weatherInfo = result.data,
                        isLoading = false,
                        error = null
                    ),
                    stateError = state.copy(
                        weatherInfo = null,
                        isLoading = false,
                        error = result.message
                    ),
                    successAction = {
                        if (listSearchQuery.contains(state.searchQuery)) {
                            listSearchQuery.remove(state.searchQuery)
                        }
                        listSearchQuery.add(state.searchQuery)
                        citiesItemList = listSearchQuery.toList()
                    }
                )
            } ?: run {
                errorLocationState()
            }
        }
    }

    private fun loadSunsetAndSunriseTimes() {
        viewModelScope.launch {
            startLoadingState()

            getProviderLocation()?.let { location ->
                val result = sunsetSunriseTimeRepository.getSunsetSunriseTime(
                    lat = location.first,
                    lon = location.second,
                    fetchFromRemote = true
                )
                loadDataUsingResource(
                    result = result,
                    stateSuccess = state.copy(
                        sunsetAndSunriseTime = result.data,
                        isLoading = false,
                        error = null
                    ),
                    stateError = state.copy(
                        sunsetAndSunriseTime = null,
                        isLoading = false,
                        error = result.message
                    )
                )
            } ?: run {
                errorLocationState()
            }
        }
    }
}