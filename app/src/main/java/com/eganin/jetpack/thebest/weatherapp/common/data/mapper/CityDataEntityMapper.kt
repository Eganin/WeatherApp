package com.eganin.jetpack.thebest.weatherapp.common.data.mapper

import com.eganin.jetpack.thebest.weatherapp.common.data.local.entities.CityDataEntity
import com.eganin.jetpack.thebest.weatherapp.common.domain.weather.WeatherData
import com.eganin.jetpack.thebest.weatherapp.detailpage.domain.sunsetsunrisetime.SunsetSunriseTimeData

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