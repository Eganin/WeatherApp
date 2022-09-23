package com.eganin.jetpack.thebest.weatherapp.data.mapper

import com.eganin.jetpack.thebest.weatherapp.data.local.entities.SunsetSunriseTimeDataEntity
import com.eganin.jetpack.thebest.weatherapp.detailpage.domain.sunsetsunrisetime.SunsetSunriseTimeData

fun SunsetSunriseTimeData.toSunsetSunriseTimeDataEntity(): SunsetSunriseTimeDataEntity {
    return SunsetSunriseTimeDataEntity(
        sunsetHour = sunsetHour,
        sunriseHour = sunriseHour
    )
}

fun SunsetSunriseTimeDataEntity.toSunsetSunriseTimeData(): SunsetSunriseTimeData {
    return SunsetSunriseTimeData(
        sunriseHour = sunriseHour,
        sunsetHour = sunsetHour
    )
}