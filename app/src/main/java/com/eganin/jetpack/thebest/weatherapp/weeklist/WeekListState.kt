package com.eganin.jetpack.thebest.weatherapp.weeklist

import com.eganin.jetpack.thebest.weatherapp.common.domain.weather.WeatherData

data class WeekListState(
    val info : Map<Int,List<WeatherData>>?=null,
    val isLoading : Boolean=false,
    val error: String?= null
)
