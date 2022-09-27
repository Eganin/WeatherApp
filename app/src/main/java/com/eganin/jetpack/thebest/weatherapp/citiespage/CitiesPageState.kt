package com.eganin.jetpack.thebest.weatherapp.citiespage

import com.eganin.jetpack.thebest.weatherapp.common.domain.weather.WeatherData
import com.eganin.jetpack.thebest.weatherapp.detailpage.domain.sunsetsunrisetime.SunsetSunriseTimeData

data class CitiesPageState(
    var isLoading: Boolean=false,
    var info : List<Triple<WeatherData, SunsetSunriseTimeData,String>>? = null,
    val error: String? = null
)
