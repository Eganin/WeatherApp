package com.eganin.jetpack.thebest.weatherapp.common.data.mapper

import android.util.Log
import com.eganin.jetpack.thebest.weatherapp.common.data.local.entities.CurrentWeatherDataEntity
import com.eganin.jetpack.thebest.weatherapp.common.data.local.entities.WeatherInfoEntity
import com.eganin.jetpack.thebest.weatherapp.common.domain.weather.WeatherData
import com.eganin.jetpack.thebest.weatherapp.common.domain.weather.WeatherInfo
import com.eganin.jetpack.thebest.weatherapp.common.domain.weather.WeatherType
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun WeatherInfo.toWeatherInfoEntity(): WeatherInfoEntity {
    val newMap = mutableMapOf<Int, List<CurrentWeatherDataEntity>>()
    weatherDataPerDay.forEach { (key, value) ->
        newMap[key] = value.map { it.toCurrentWeatherDataEntity() }
    }

    return WeatherInfoEntity(
        weatherDataPerDay = newMap,
        currentWeatherData = currentWeatherData?.toCurrentWeatherDataEntity(),
    )
}

fun WeatherInfoEntity.toWeatherInfo(): WeatherInfo {
    val newMap = mutableMapOf<Int, List<WeatherData>>()
    weatherDataPerDay.forEach { (key, value) ->
        newMap[key] = value.map { it.toWeatherData() }
    }

    return WeatherInfo(
        weatherDataPerDay = newMap,
        currentWeatherData = currentWeatherData?.toWeatherData()
    )
}

fun WeatherData.toCurrentWeatherDataEntity(): CurrentWeatherDataEntity {
    return CurrentWeatherDataEntity(
        time = time.toString(),
        temperatureCelsius = temperatureCelsius,
        pressure = pressure,
        windSpeed = windSpeed,
        humidity = humidity,
        weatherCode = weatherCode,
        state = state
    )
}

fun CurrentWeatherDataEntity.toWeatherData(): WeatherData {
    return WeatherData(
        time = LocalDateTime.parse(time, DateTimeFormatter.ISO_DATE_TIME),
        temperatureCelsius = temperatureCelsius,
        pressure = pressure,
        windSpeed = windSpeed,
        humidity = humidity,
        weatherType = WeatherType.fromWMO(code = weatherCode),
        weatherCode = weatherCode,
        state = state
    )
}