package com.eganin.jetpack.thebest.weatherapp.detailpage.data.mapper

import android.icu.util.GregorianCalendar
import com.eganin.jetpack.thebest.weatherapp.detailpage.data.remote.ResultsSunsetSunriseTimeDto
import com.eganin.jetpack.thebest.weatherapp.detailpage.domain.sunsetsunrisetime.SunsetSunriseTimeData
import java.util.concurrent.TimeUnit


fun ResultsSunsetSunriseTimeDto.toSunsetAndSunriseTime(): SunsetSunriseTimeData {
    val calendar = GregorianCalendar()
    val mGmtOffset = calendar.timeZone.rawOffset
    val gmtTimeToHours = TimeUnit.HOURS.convert(mGmtOffset.toLong(), TimeUnit.MILLISECONDS).toInt()

    val sunsetHour = sunsetTime.split(":")[0].toInt()+12+gmtTimeToHours
    val sunriseHour = sunriseTime.split(":")[0].toInt() + gmtTimeToHours

    return SunsetSunriseTimeData(
        sunsetHour = sunsetHour,
        sunriseHour = sunriseHour
    )
}