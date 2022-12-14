package com.example.data.mapper

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.data.remote.WeatherDataDto
import com.example.data.remote.WeatherDto
import com.example.domain.models.weather.WeatherData
import com.example.domain.models.weather.WeatherInfo
import com.example.domain.models.weather.WeatherType
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt

private data class IndexedWeatherData(
    val index: Int,
    val data: WeatherData
)

/*
 method return map with daytime and hourly weather information
 */
@RequiresApi(Build.VERSION_CODES.O)
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
                weatherCode=weatherCode,
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

@RequiresApi(Build.VERSION_CODES.O)
fun WeatherDto.toWeatherInfo(): WeatherInfo {
    val weatherDataMap = weatherData.toWeatherDataMap()
    val now = LocalDateTime.now()
    // get info for current day
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

/*
 method return average value for day
 */
fun List<WeatherData>.toAverageValues(): List<Int> {
    var nightCelsius = 0.0
    var morningCelsius = 0.0
    var dayCelsius = 0.0
    var eveningCelsius = 0.0
    forEachIndexed { index, weatherData ->
        if (index in 6..11) {
            morningCelsius += weatherData.temperatureCelsius
        } else if (index in 12..17) {
            dayCelsius += weatherData.temperatureCelsius
        } else if (index in 18..23) {
            eveningCelsius += weatherData.temperatureCelsius
        } else {
            nightCelsius += weatherData.temperatureCelsius
        }
    }
    return listOf(
        -(morningCelsius/6).roundToInt(),
        -(dayCelsius/6).roundToInt(),
        -(eveningCelsius/6).roundToInt(),
        -(nightCelsius/6).roundToInt()
    )
}