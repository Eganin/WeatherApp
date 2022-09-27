package com.eganin.jetpack.thebest.weatherapp.common.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.eganin.jetpack.thebest.weatherapp.common.data.local.entities.SunsetSunriseTimeDataEntity

@Dao
interface SunsetSunriseTimeDataDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSunsetAndSunriseInfo(sunsetSunriseEntity: SunsetSunriseTimeDataEntity)

    @Query("DELETE FROM sunsetsunrisetimedataentity")
    suspend fun clearSunsetAndSunriseInfo()

    @Query("SELECT * FROM sunsetsunrisetimedataentity")
    suspend fun getSunsetAndSunriseInfo() : SunsetSunriseTimeDataEntity
}