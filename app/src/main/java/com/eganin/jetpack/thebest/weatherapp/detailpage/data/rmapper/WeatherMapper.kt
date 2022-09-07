package com.eganin.jetpack.thebest.weatherapp.detailpage.data.rmapper

import com.eganin.jetpack.thebest.weatherapp.detailpage.data.remote.WeatherDataDto
import com.eganin.jetpack.thebest.weatherapp.detailpage.data.remote.WeatherDto
import com.eganin.jetpack.thebest.weatherapp.detailpage.domain.weather.WeatherData
import com.eganin.jetpack.thebest.weatherapp.detailpage.domain.weather.WeatherInfo
import com.eganin.jetpack.thebest.weatherapp.detailpage.domain.weather.WeatherType
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

private data class IndexedWeatherData(
    val index: Int,
    val data: WeatherData
)

fun WeatherDataDto.toWeatherDataMap(): Map<Int, List<WeatherData>> {
    return time.mapIndexed { index, time ->
        val temperature = temperatures[index]
        val weatherCode = weatherCodes[index]
        val windSpeed = winsSpeeds[index]
        val pressure = pressures[index]
        val humidity = humidities[index]
        IndexedWeatherData(
            index = index,
            data = WeatherData(
                time = LocalDateTime.parse(time, DateTimeFormatter.ISO_DATE_TIME),
                temperatureCelsius = temperature,
                pressure = pressure,
                windSpeed = windSpeed,
                humidity = humidity,
                weatherType = WeatherType.fromWMO(code = weatherCode)
            )
        )
    }.groupBy { indexedData ->
        indexedData.index / 24
    }.mapValues { mapEntry ->
        mapEntry.value.map {
            it.data
        }
    }
}

fun WeatherDto.toWeatherInfo(): WeatherInfo {
    val weatherDataMap = weatherData.toWeatherDataMap()
    val now = LocalDateTime.now()
    val currentWeatherData = weatherDataMap[0]?.find {
        val hour = if (now.hour == 23) {
            if (now.minute < 30) now.hour else 0
        } else {
            if (now.minute < 30) now.hour else now.hour + 1
        }
        it.time.hour == hour
    }
    return WeatherInfo(
        weatherDataPerDay = weatherDataMap,
        currentWeatherData = currentWeatherData
    )
}