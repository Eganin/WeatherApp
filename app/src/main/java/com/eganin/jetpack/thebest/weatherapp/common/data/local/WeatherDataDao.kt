package com.eganin.jetpack.thebest.weatherapp.common.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.eganin.jetpack.thebest.weatherapp.common.data.local.entities.WeatherDataEntity

@Dao
interface WeatherDataDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeatherData(weatherData : WeatherDataEntity)

    @Query("DELETE FROM weatherdataentity")
    suspend fun clearWeatherData()

    @Query("SELECT * FROM weatherdataentity")
    suspend fun getWeatherDataInfo() : WeatherDataEntity
}