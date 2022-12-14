package com.example.data.local.converters

import androidx.room.TypeConverter
import com.example.data.local.entities.CurrentWeatherDataEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class CurrentWeatherDataConverter {

    @TypeConverter
    fun weatherInfoEntityToString(data: Map<Int, List<CurrentWeatherDataEntity>>): String {
        val gson = Gson()
        val type= object : TypeToken<Map<Int, List<CurrentWeatherDataEntity>>>() {}.type
        val result : String = gson.toJson(data, type)
        return result
    }

    @TypeConverter
    fun stringToWeatherInfoEntity(str: String): Map<Int, List<CurrentWeatherDataEntity>> {
        val gson = Gson()
        val type = object : TypeToken<Map<Int, List<CurrentWeatherDataEntity>>>() {}.type
        val result :Map<Int, List<CurrentWeatherDataEntity>> = gson.fromJson(str, type)
        return result
    }
 }