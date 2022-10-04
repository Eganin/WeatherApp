package com.eganin.jetpack.thebest.weatherapp.presentation.citiespage

import com.example.domain.models.weather.WeatherData
import com.example.domain.models.sunsetsunrisetime.SunsetSunriseTimeData

data class CitiesPageState(
    var isLoading: Boolean=false,
    var info : List<Triple<WeatherData, SunsetSunriseTimeData,String>>? = null,
    val error: String? = null
)
