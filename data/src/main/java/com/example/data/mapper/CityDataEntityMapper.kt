package com.example.data.mapper

import com.example.data.local.entities.CityDataEntity
import com.example.domain.models.sunsetsunrisetime.SunsetSunriseTimeData
import com.example.domain.models.weather.WeatherData

fun Triple<WeatherData?, SunsetSunriseTimeData?,String>.toCityDataEntity(): CityDataEntity {
    return CityDataEntity(
        currentWeatherDataEntity = first?.toCurrentWeatherDataEntity(),
        sunsetHour = second?.sunsetHour ?: 0,
        sunriseHour = second?.sunriseHour ?: 0,
        cityName = third
    )
}

fun CityDataEntity.toTriple(): Triple<WeatherData?, SunsetSunriseTimeData?,String> {
    return Triple(
        first = currentWeatherDataEntity?.toWeatherData(),
        second = SunsetSunriseTimeData(sunriseHour = sunriseHour, sunsetHour = sunsetHour),
        third = cityName
    )
}