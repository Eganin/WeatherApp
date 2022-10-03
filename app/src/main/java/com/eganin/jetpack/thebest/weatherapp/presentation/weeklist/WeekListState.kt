package com.eganin.jetpack.thebest.weatherapp.presentation.weeklist

import com.example.domain.models.weather.WeatherData

data class WeekListState(
    val info : List<WeatherData>?=null,
    val isLoading : Boolean=false,
    val error: String?= null
)
