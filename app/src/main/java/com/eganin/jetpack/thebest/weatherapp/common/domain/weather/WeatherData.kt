package com.eganin.jetpack.thebest.weatherapp.common.domain.weather

import java.time.LocalDateTime

data class WeatherData(
    val time: LocalDateTime,
    val temperatureCelsius: Double,
    val pressure: Double,
    val windSpeed: Double,
    val humidity: Double,
    val weatherType: WeatherType,
    val weatherCode : Int,
    val state: WeatherState = when (weatherType.weatherDesc) {
        "Clear sky" -> WeatherState.CLEAR_SKY
        "Mainly clear" -> WeatherState.FEW_CLOUDS
        "Partly cloudy" -> WeatherState.SCATTERED_CLOUDS
        "Overcast" -> WeatherState.MOSTLY_CLOUDY
        "Foggy", "Depositing rime fog" -> WeatherState.FOG
        "Light drizzle", "Moderate drizzle", "Dense drizzle", "Slight rain", "Slight rain showers" -> WeatherState.RAIN
        "Rainy", "Heavy rain", "Heavy freezing rain", "Moderate rain showers", "Violent rain showers" -> WeatherState.HEAVY_RAIN
        "Moderate thunderstorm", "Thunderstorm with slight hail", "Thunderstorm with heavy hail" -> WeatherState.THUNDERSTORM
        else -> WeatherState.SNOW
    }
)


enum class WeatherState {
    CLEAR_SKY,
    FEW_CLOUDS,
    SCATTERED_CLOUDS,
    MOSTLY_CLOUDY,
    HEAVY_RAIN,
    RAIN,
    THUNDERSTORM,
    SNOW,
    FOG,
}