package com.example.domain.models.weather

data class WeatherInfo(
    val weatherDataPerDay : Map<Int , List<WeatherData>>,
    val currentWeatherData : WeatherData?
)
