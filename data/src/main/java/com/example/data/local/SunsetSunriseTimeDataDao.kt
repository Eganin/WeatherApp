package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.local.entities.SunsetSunriseTimeDataEntity

@Dao
interface SunsetSunriseTimeDataDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSunsetAndSunriseInfo(sunsetSunriseEntity: SunsetSunriseTimeDataEntity)

    @Query("DELETE FROM sunsetsunrisetimedataentity")
    suspend fun clearSunsetAndSunriseInfo()

    @Query("SELECT * FROM sunsetsunrisetimedataentity")
    suspend fun getSunsetAndSunriseInfo() : SunsetSunriseTimeDataEntity
}