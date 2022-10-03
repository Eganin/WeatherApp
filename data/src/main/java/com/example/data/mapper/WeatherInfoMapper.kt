package com.example.data.mapper

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.data.local.entities.CurrentWeatherDataEntity
import com.example.data.local.entities.WeatherInfoEntity
import com.example.domain.models.weather.WeatherData
import com.example.domain.models.weather.WeatherInfo
import com.example.domain.models.weather.WeatherType
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

@RequiresApi(Build.VERSION_CODES.O)
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

@RequiresApi(Build.VERSION_CODES.O)
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