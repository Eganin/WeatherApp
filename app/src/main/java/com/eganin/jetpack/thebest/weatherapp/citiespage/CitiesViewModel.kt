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
class CitiesViewModel  @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val geocodingRepository: GeocodingRepository,
    private val sunsetSunriseTimeRepository: SunsetSunriseTimeRepository
) : ViewModel(){

    var state by mutableStateOf(CitiesPageState())
        private set

    fun loadDataForCitiesPage(listSearchQuery : List<String>) {
        viewModelScope.launch {
            state = state.copy(
                isLoading = true,
                error = null,
            )
            val listInfo: MutableList<Pair<WeatherData, SunsetSunriseTimeData>> = mutableListOf()
            listSearchQuery.forEach { name ->
                geocodingRepository.getGeoFromCity(cityName = name).data?.let { coordinates ->
                    var weatherData: WeatherData? = null
                    var sunsetSunriseTimeData: SunsetSunriseTimeData? = null
                    when (val result =
                        sunsetSunriseTimeRepository.getSunsetSunriseTime(
                            lat = coordinates.latitude,
                            lon = coordinates.longitude
                        )) {

                        is Resource.Success -> {
                            sunsetSunriseTimeData = result.data
                        }
                        is Resource.Error -> {
                            state = state.copy(
                                isLoading = false,
                                error = result.message
                            )
                        }
                    }

                    when (val result =
                        weatherRepository.getWeatherData(
                            coordinates.latitude,
                            coordinates.longitude
                        )) {
                        is Resource.Success -> {
                            result.data?.currentWeatherData?.let {
                                weatherData = it
                            }
                        }
                        is Resource.Error -> {
                            state = state.copy(
                                isLoading = false,
                                error = result.message
                            )
                        }
                    }

                    weatherData?.let { data ->
                        sunsetSunriseTimeData?.let { sunsetAndSunrise ->
                            listInfo.add(Pair(first = data, second = sunsetAndSunrise))
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
            }else{
                state.copy(
                    info = null,
                    isLoading = false,
                    error = null,
                )
            }
        }
    }
}