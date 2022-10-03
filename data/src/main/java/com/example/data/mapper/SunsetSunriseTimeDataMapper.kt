package com.example.data.mapper

import com.example.data.local.entities.SunsetSunriseTimeDataEntity
import com.example.domain.models.sunsetsunrisetime.SunsetSunriseTimeData

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