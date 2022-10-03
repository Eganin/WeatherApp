package com.example.data.local.converters

import androidx.room.TypeConverter

class WeatherDataConverter {

    @TypeConverter
    fun listStringsToString(time: List<String>): String {
        return time.joinToString(separator = ",")
    }

    @TypeConverter
    fun stringToListStrings(str: String): List<String> {
        return str.split(",")
    }

    @TypeConverter
    fun listDoublesToString(temperatures: List<Double>): String {
        return temperatures.joinToString(separator = ",")
    }

    @TypeConverter
    fun stringToListDoubles(str: String): List<Double> {
        return str.split(",").map {
            try {
                it.toDouble()
            } catch (e: Exception) {
                0.0
            }
        }
    }


}