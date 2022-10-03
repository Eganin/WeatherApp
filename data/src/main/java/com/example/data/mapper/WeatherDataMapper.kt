package com.example.data.mapper

import com.example.data.local.entities.WeatherDataEntity
import com.example.data.remote.WeatherDataDto

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