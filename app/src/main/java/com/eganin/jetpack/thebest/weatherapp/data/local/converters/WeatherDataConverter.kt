package com.eganin.jetpack.thebest.weatherapp.data.local.converters

import androidx.room.TypeConverter
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class WeatherDataConverter {

    @TypeConverter
    fun timeToString(time : LocalDateTime): String {
        val hour = time.hour
        val month = time.monthValue
        return "${time.year}-${if(month <10) "0$month" else month}-${time.dayOfMonth}T${if(hour<10) "0$hour" else hour}:00"
    }

    @TypeConverter
    fun stringToTime(time : String) : LocalDateTime{
        return LocalDateTime.parse(time, DateTimeFormatter.ISO_DATE_TIME)
    }
}