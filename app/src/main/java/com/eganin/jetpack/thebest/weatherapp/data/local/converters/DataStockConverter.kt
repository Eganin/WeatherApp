package com.eganin.jetpack.thebest.weatherapp.data.local.converters

import androidx.room.TypeConverter
import java.lang.Exception

class DataStockConverter {

    @TypeConverter
    fun dataToString(list: List<Int>): String {
        return list.joinToString(separator = ",")
    }

    fun stringToData(str : String) : List<Int>{
        return str.split(",").map {
            try {
                it.toInt()
            }catch (e : Exception){
                0
            }
        }
    }
}