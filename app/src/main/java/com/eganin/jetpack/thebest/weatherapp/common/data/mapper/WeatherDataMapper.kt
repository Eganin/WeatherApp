package com.eganin.jetpack.thebest.weatherapp.common.data.mapper

import com.eganin.jetpack.thebest.weatherapp.common.data.remote.WeatherDataDto
import com.eganin.jetpack.thebest.weatherapp.common.data.local.entities.WeatherDataEntity

fun WeatherDataDto.toWeatherDataEntity(): WeatherDataEntity {
    return WeatherDataEntity(
        time = time,
        temperatures = temperatures,
        weatherCodes = weatherCodes,
        pressures = pressures,
        winsSpeeds = winsSpeeds,
        humidities = humidities
    )
}

fun WeatherDataEntity.toWeatherDataDto(): WeatherDataDto {
    return WeatherDataDto(
        time = time,
        temperatures = temperatures,
        weatherCodes = weatherCodes,
        pressures = pressures,
        winsSpeeds = winsSpeeds,
        humidities = humidities
    )
}