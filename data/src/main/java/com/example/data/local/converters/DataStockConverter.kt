package com.example.data.local.converters

import androidx.room.TypeConverter
import java.lang.Exception

class DataStockConverter {

    @TypeConverter
    fun listIntsToString(list: List<Int>): String {
        return list.joinToString(separator = ",")
    }

    @TypeConverter
    fun stringToListInts(str : String) : List<Int>{
        return str.split(",").map {
            try {
                it.toInt()
            }catch (e : Exception){
                0
            }
        }
    }
}