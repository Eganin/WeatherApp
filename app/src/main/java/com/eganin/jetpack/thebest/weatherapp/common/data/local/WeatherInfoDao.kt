package com.eganin.jetpack.thebest.weatherapp.common.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.eganin.jetpack.thebest.weatherapp.common.data.local.entities.WeatherInfoEntity

@Dao
interface WeatherInfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeatherInfo(weatherInfoEntity : WeatherInfoEntity)

    @Query("DELETE FROM weatherinfoentity")
    suspend fun clearWeatherInfo()

    @Query("SELECT * FROM weatherinfoentity")
    suspend fun getWeatherInfo() : WeatherInfoEntity
}