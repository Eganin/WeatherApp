package com.eganin.jetpack.thebest.weatherapp.presentation.citiespage

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eganin.jetpack.thebest.weatherapp.presentation.detailpage.DetailPageEvent
import com.example.domain.repository.WeatherRepository
import com.example.domain.util.Resource
import com.example.domain.models.weather.WeatherData
import com.example.domain.models.sunsetsunrisetime.SunsetSunriseTimeData
import com.example.domain.usecase.WeatherUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CitiesViewModel @Inject constructor(
    private val weatherUseCases: WeatherUseCases,
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
                    firstLaunch = true
                    loadDataForCitiesPage()
                }
                val searchQuery = event.info?.filter { it !in listSearchQuery }
                if (searchQuery?.isNotEmpty() == true) loadDataForCitiesPage(listSearchQuery = searchQuery)
            }

            is CitiesPageEvent.Error -> {
                state = state.copy(
                    isLoading = false,
                    error = event.message
                )
            }
        }
    }

    private fun updatePrivateSearchQuery() {
        viewModelScope.launch {
            weatherUseCases.getCity().collect { result ->
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
                loadData(fetchFromRemote = fetchFromRemote, cityName = "") {
                    listInfo.add(it)
                }
            }
            firstLaunch = false
            listSearchQuery.forEach { name ->
                loadData(fetchFromRemote = fetchFromRemote, cityName = name) {
                    listInfo.add(it)
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

    private suspend fun loadData(
        fetchFromRemote: Boolean,
        cityName: String,
        onSuccess: (Triple<WeatherData, SunsetSunriseTimeData, String>) -> Unit
    ) {
        weatherUseCases.getDataForCitiesPage(
            cityName = cityName,
            fetchFromRemote = fetchFromRemote
        )
            .collect { result ->
                wrapperForHandlerResource(result = result) { list ->
                    list.forEach {
                        it.first?.let { data ->
                            it.second?.let { sunsetSunriseTimeData ->
                                onSuccess(
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
                result.message?.let {
                    onEvent(event = CitiesPageEvent.Error(message = it))
                }?: run {
                    onEvent(event = CitiesPageEvent.Error(message = "Error"))
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