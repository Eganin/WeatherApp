package com.example.data.mapper

import android.icu.util.GregorianCalendar
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.data.remote.ResultsSunsetSunriseTimeDto
import com.example.domain.models.sunsetsunrisetime.SunsetSunriseTimeData
import java.util.concurrent.TimeUnit


@RequiresApi(Build.VERSION_CODES.N)
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