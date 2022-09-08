package com.eganin.jetpack.thebest.weatherapp.common.domain.weather

data class WeatherInfo(
    val weatherDataPerDay : Map<Int , List<WeatherData>>,
    val currentWeatherData : WeatherData?
)
