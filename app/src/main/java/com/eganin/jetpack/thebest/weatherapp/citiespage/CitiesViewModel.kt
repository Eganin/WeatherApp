package com.eganin.jetpack.thebest.weatherapp.citiespage

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eganin.jetpack.thebest.weatherapp.common.domain.repository.WeatherRepository
import com.eganin.jetpack.thebest.weatherapp.common.domain.util.Resource
import com.eganin.jetpack.thebest.weatherapp.common.domain.weather.WeatherData
import com.eganin.jetpack.thebest.weatherapp.detailpage.domain.repository.GeocodingRepository
import com.eganin.jetpack.thebest.weatherapp.detailpage.domain.repository.SunsetSunriseTimeRepository
import com.eganin.jetpack.thebest.weatherapp.detailpage.domain.sunsetsunrisetime.SunsetSunriseTimeData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CitiesViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository,
) : ViewModel() {

    var state by mutableStateOf(CitiesPageState())
        private set

    private var firstLaunch = true

    private var listSearchQuery = mutableSetOf<String>()

    init {
        updatePrivateSearchQuery()
    }

    fun onEvent(event: CitiesPageEvent) {
        when (event) {
            is CitiesPageEvent.LoadData -> {
                if (event.info == null) {
                    firstLaunch=true
                    loadDataForCitiesPage()
                }
                val searchQuery = event.info?.filter { it !in listSearchQuery }
                if (searchQuery?.isNotEmpty() == true) loadDataForCitiesPage(listSearchQuery = searchQuery)
            }
            is CitiesPageEvent.Error -> {
                state = state.copy(
                    isLoading = false,
                    error = "Error Loading cities info"
                )
            }
        }
    }

    private fun updatePrivateSearchQuery() {
        viewModelScope.launch {
            weatherRepository.getCityNameFromDB().collect { result ->
                wrapperForHandlerResource(result = result, onStateChangeSuccess = {
                    if (it.isNotEmpty()) listSearchQuery = it as MutableSet<String>
                })
            }
        }
    }

    private fun loadDataForCitiesPage(listSearchQuery: List<String> = emptyList()) {
        viewModelScope.launch {
            startLoadingState()

            val listInfo: MutableList<Triple<WeatherData, SunsetSunriseTimeData, String>> =
                mutableListOf()
            val fetchFromRemote = !firstLaunch
            if (firstLaunch) {
                weatherRepository.getDataForCitiesPage(
                    cityName = "",
                    fetchFromRemote = fetchFromRemote
                )
                    .collect { result ->
                        wrapperForHandlerResource(result = result) { list ->
                            list.forEach {
                                it.first?.let { data ->
                                    it.second?.let { sunsetSunriseTimeData ->
                                        listInfo.add(
                                            Triple(
                                                first = data,
                                                second = sunsetSunriseTimeData,
                                                third = it.third
                                            )
                                        )
                                    }
                                }
                            }
                        }
                    }
            }
            firstLaunch = false
            listSearchQuery.forEach { name ->
                weatherRepository.getDataForCitiesPage(
                    cityName = name,
                    fetchFromRemote = fetchFromRemote
                )
                    .collect { result ->
                        wrapperForHandlerResource(result = result) { list ->
                            list.forEach {
                                it.first?.let { data ->
                                    it.second?.let { sunsetSunriseTimeData ->
                                        listInfo.add(
                                            Triple(
                                                first = data,
                                                second = sunsetSunriseTimeData,
                                                third = it.third
                                            )
                                        )
                                    }
                                }
                            }
                        }
                    }
            }
            updatePrivateSearchQuery()
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