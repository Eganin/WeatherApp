package com.example.domain.usecase

data class WeatherUseCases(
    val getCity: GetCityFromDb,
    val getWeatherInfo: GetWeatherInfo,
    val getDataStock: GetDataStock,
    val getSunsetAndSunriseTimes: GetSunsetAndSunriseTimes,
    val getCoordinatesFromCity : GetCoordinatesFromCityName
)
