package com.eganin.jetpack.thebest.weatherapp.detailpage.data.mapper

import com.eganin.jetpack.thebest.weatherapp.detailpage.data.remote.ResultsSunsetSunriseTimeDto
import com.eganin.jetpack.thebest.weatherapp.detailpage.domain.sunsetsunrisetime.SunsetSunriseTimeData


fun ResultsSunsetSunriseTimeDto.toSunsetAndSunriseTime() : SunsetSunriseTimeData {
    val sunsetHour = solarNoon.split(":")[0].toInt()
    val sunriseHour = sunsetHour + dayLength.split(":")[0].toInt()

    return SunsetSunriseTimeData(
        sunsetHour = sunsetHour,
        sunriseHour =sunriseHour
    )
}