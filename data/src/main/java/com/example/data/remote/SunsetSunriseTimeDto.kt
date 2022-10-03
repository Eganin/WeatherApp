package com.example.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SunsetSunriseTimeDto(
    @SerialName("results")
    val results : ResultsSunsetSunriseTimeDto
)
@Serializable
data class ResultsSunsetSunriseTimeDto(
    @SerialName("sunrise")
    val sunriseTime : String = "",

    @SerialName("sunset")
    val sunsetTime : String = "",

    @SerialName("solar_noon")
    val solarNoon : String ="",

    @SerialName("day_length")
    val dayLength : String = ""
)
