package com.eganin.jetpack.thebest.weatherapp.citiespage

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eganin.jetpack.thebest.weatherapp.common.domain.repository.WeatherRepository
import com.eganin.jetpack.thebest.weatherapp.common.domain.util.Resource
import com.eganin.jetpack.thebest.weatherapp.common.domain.weather.WeatherData
import com.eganin.jetpack.thebest.weatherapp.common.presentation.getProviderLocation
import com.eganin.jetpack.thebest.weatherapp.detailpage.domain.repository.GeocodingRepository
import com.eganin.jetpack.thebest.weatherapp.detailpage.domain.repository.SunsetSunriseTimeRepository
import com.eganin.jetpack.thebest.weatherapp.detailpage.domain.sunsetsunrisetime.SunsetSunriseTimeData
import com.eganin.jetpack.thebest.weatherapp.weeklist.WeekListEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CitiesViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val geocodingRepository: GeocodingRepository,
    private val sunsetSunriseTimeRepository: SunsetSunriseTimeRepository
) : ViewModel() {

    var state by mutableStateOf(CitiesPageState())
        private set

    fun onEvent(event: CitiesPageEvent) {
        when (event) {
            is CitiesPageEvent.LoadData -> {
                loadDataForCitiesPage(listSearchQuery = event.info)
            }
            is CitiesPageEvent.Error -> {
                state = state.copy(
                    isLoading = false,
                    error = "Error Loading cities info"
                )
            }
        }
    }

    private fun loadDataForCitiesPage(listSearchQuery: List<String>) {
        viewModelScope.launch {
            startLoadingState()

            val listInfo: MutableList<Pair<WeatherData, SunsetSunriseTimeData>> = mutableListOf()
            var weatherData: WeatherData? = null
            var sunsetSunrise: SunsetSunriseTimeData? = null

            listSearchQuery.forEach { name ->
                geocodingRepository.getGeoFromCity(
                    cityName = name,
                    fetchFromRemote = true
                ).collect { geocodingDto ->
                    wrapperForHandlerResource(result = geocodingDto) { coordinates ->
                        sunsetSunriseTimeRepository.getSunsetSunriseTime(
                            lat = coordinates.latitude,
                            lon = coordinates.longitude,
                            fetchFromRemote = true
                        ).collect { sunsetSunriseTimeData ->
                            wrapperForHandlerResource(result = sunsetSunriseTimeData) {
                                sunsetSunrise = it
                            }
                        }

                        weatherRepository.getWeatherData(
                            coordinates.latitude,
                            coordinates.longitude,
                            fetchFromRemote = true
                        ).collect { weatherInfo ->
                            wrapperForHandlerResource(result = weatherInfo) {
                                it.currentWeatherData?.let {
                                    weatherData = it
                                }
                            }
                        }

                        weatherData?.let { data ->
                            sunsetSunrise?.let { sunsetAndSunrise ->
                                listInfo.add(Pair(first = data, second = sunsetAndSunrise))
                            }
                        }
                    }

                }
            }
            state = if (listInfo.isNotEmpty()) {
                state.copy(
                    info = listInfo,
                    isLoading = false,
                    error = null,
                )
            } else {
                state.copy(
                    info = null,
                    isLoading = false,
                    error = null,
                )
            }
        }
    }

    private suspend fun <T> wrapperForHandlerResource(
        result: Resource<T>,
        onStateChangeSuccess: suspend (T) -> Unit
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
        onEvent(event = CitiesPageEvent.Error)
    }
}