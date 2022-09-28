package com.eganin.jetpack.thebest.weatherapp.detailpage.presentation.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eganin.jetpack.thebest.weatherapp.common.domain.location.LocationTracker
import com.eganin.jetpack.thebest.weatherapp.common.domain.repository.WeatherRepository
import com.eganin.jetpack.thebest.weatherapp.common.domain.util.Resource
import com.eganin.jetpack.thebest.weatherapp.common.presentation.getProviderLocation
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

    var citiesItemList by mutableStateOf(listOf<String>())

    var state by mutableStateOf(WeatherState())
        private set

    private var searchJob: Job? = null

    private var listSearchQuery = mutableSetOf<String>()

    private val fakeData = Pair(first = 0.0, second = 0.0)

    init {
        viewModelScope.launch {
            repository.getCityNameFromDB().collect{result->
                wrapperForHandlerResource(result = result, onStateChangeSuccess = {
                    if(it.isNotEmpty()) listSearchQuery= it as MutableSet<String>
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

    private fun loadWeatherInfo() {
        viewModelScope.launch {
            startLoadingState()

            val (data, fetchFromRemote) = provideDataForRepository()

            repository.getWeatherData(
                lat = data.first,
                long = data.second,
                fetchFromRemote = fetchFromRemote
            ).collect { result ->
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

            val (data, fetchFromRemote) = provideDataForRepository()
            repository.getDataForStock(
                lat = data.first,
                long = data.second,
                fetchFromRemote = fetchFromRemote
            ).collect { result ->
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

            val (data, fetchFromRemote) = provideDataForRepository()

            // get geocoding from city
            geocodingRepository.getGeoFromCity(
                cityName = cityName,
                fetchFromRemote = fetchFromRemote
            ).collect {
                data.let { location ->
                    repository.getWeatherData(
                        lat = location.first,
                        long = location.second,
                        fetchFromRemote = fetchFromRemote
                    ).collect { result ->
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
    }

    private fun loadSunsetAndSunriseTimes() {
        viewModelScope.launch {
            startLoadingState()

            val (data, fetchFromRemote) = provideDataForRepository()

            sunsetSunriseTimeRepository.getSunsetSunriseTime(
                lat = data.first,
                lon = data.second,
                fetchFromRemote = fetchFromRemote
            ).collect { result ->
                wrapperForHandlerResource(result = result, onStateChangeSuccess = {
                    state = state.copy(sunsetAndSunriseTime = it, isLoading = false, error = null)
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
                state = state.copy(isLoading = false, error = result.message)
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
        onEvent(event = DetailPageEvent.Error)
    }

    private suspend fun provideDataForRepository(): Pair<Pair<Double, Double>, Boolean> {
        getProviderLocation(
            searchQuery = state.searchQuery,
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