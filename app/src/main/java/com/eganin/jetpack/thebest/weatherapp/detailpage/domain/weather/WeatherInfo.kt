package com.eganin.jetpack.thebest.weatherapp.detailpage.domain.weather

data class WeatherInfo(
    val weatherDataPerDay : Map<Int , List<WeatherData>>,
    val currentWeatherData : WeatherData?
)
